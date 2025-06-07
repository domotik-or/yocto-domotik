Booter en pxe
=============

Note: ne fonctionne pas... Seul le DHCP a fonctionné.

Côté Raspberry Pi
-----------------

Le Raspberry Pi est connecté à Internet.

Mise à jour de l'eeprom avec le dernier firmware :

.. code-block:: console

    apt update
    apt upgrade
    cd /lib/firmware/raspberrypi/bootloader/beta/
    cp pieeprom-2023-01-04.bin new-pieeprom-2023-01-04.bin
    rpi-eeprom-config new-pieeprom.bin > bootconf.txt

Modifier bootconf.txt :

D'abord SD, ensuite USB puis réseau et boucler.

.. code-block:: console

    BOOT_ORDER=0xf241
    BOOT_UART=1
    TFTP_IP=192.168.1.100

Et inscrire la configuration en eeprom :

.. code-block:: console

    rpi-eeprom-config --out netboot-pieeprom-2023-01-04.bin --config bootconf.txt new-pieeprom-2023-01-04.bin
    rpi-eeprom-update -d -f ./netboot-pieeprom-2023-01-04.bin

Rebooter et vérifier l'inscription des paramètres :

.. code-block:: console

    vcgencmd bootloader_config

Informations utiles :

- obtenir le numéro de série :

.. code-block:: console

    grep Serial /proc/cpuinfo | cut -d ' ' -f 2 | cut -c 8-16

Côté serveur
------------

Une interface réseau est dédiée au PXE. Le Raspberry Pi y est connecté.

Dans le Network Manager, fixer l'adresse de l'interface réseau en dur à
192.168.1.100, ip v4 uniquement.

Packages à installer :

- kpartx ;
- nfs-kernel-server ;
- isc-dhcp-server;
- tftpd-hpa.

Procédure
~~~~~~~~~

**Note** : le numéro de série `928ec60b` et l'interface réseau `enxa0cec85db1b2`
sont à adapter.

Créer les répertoires :

.. code-block:: console

    sudo mkdir -p /srv/tftp/928ec60b
    sudo mkdir -p /srv/nfs/raspi4b

Copier les fichiers de boot :

    cd ~/tmp/iso
    wget https://downloads.raspberrypi.org/raspios_lite_arm64/images/raspios_lite_arm64-2023-02-22/2023-02-21-raspios-bullseye-arm64-lite.img.xz
    unxz 2023-02-21-raspios-bullseye-arm64-lite.img.xz
    sudo kpartx -a -v 2023-02-21-raspios-bullseye-arm64-lite.img
    mkdir /tmp/rootmnt
    mkdir /tmp/bootmnt
    sudo mount /dev/mapper/loop0p2 /tmp/rootmnt/
    sudo mount /dev/mapper/loop0p1 /tmp/bootmnt/
    sudo cp -a /tmp/rootmnt/* /srv/nfs/raspi4b/
    sudo cp -a /tmp/bootmnt/* /srv/nfs/raspi4b/boot/

Mise à jour des fichiers binaires :

.. code-block:: console

    cd /srv/nfs/raspi4b/boot
    sudo rm start4.elf
    sudo rm fixup4.dat
    sudo wget https://github.com/Hexxeh/rpi-firmware/raw/master/start4.elf
    sudo wget https://github.com/Hexxeh/rpi-firmware/raw/master/fixup4.dat

Effectuer un bind, modifier `/etc/fstab` :

Ajouter à la fin :

.. code-block:: console

    /srv/nfs/raspi4b/boot /srv/tftp/928ec60b none defaults,bind 0 0

puis effectuer le bind pour peupler /srv/tftp/928ec60b avec les fichiers de boot
:

.. code-block:: console

    sudo mount /srv/tftp/928ec60b/

- Configurer le montage du lecteur réseau NFS, modifier `/etc/exports` :

.. code-block:: console

    /srv/nfs/raspi4b *(rw,sync,no_subtree_check,no_root_squash)

Configurer le serveur DHCP :

- modifier le fichier `/etc/default/isc-dhcp-server`. Adapter `INTERFACESv4` :

.. code-block:: console

    # Defaults for isc-dhcp-server (sourced by /etc/init.d/isc-dhcp-server)

    # Path to dhcpd's config file (default: /etc/dhcp/dhcpd.conf).
    #DHCPDv4_CONF=/etc/dhcp/dhcpd.conf
    #DHCPDv6_CONF=/etc/dhcp/dhcpd6.conf

    # Path to dhcpd's PID file (default: /var/run/dhcpd.pid).
    #DHCPDv4_PID=/var/run/dhcpd.pid
    #DHCPDv6_PID=/var/run/dhcpd6.pid

    # Additional options to start dhcpd with.
    #	Don't use options -cf or -pf here; use DHCPD_CONF/ DHCPD_PID instead
    #OPTIONS=""

    # On what interfaces should the DHCP server (dhcpd) serve DHCP requests?
    #	Separate multiple interfaces with spaces, e.g. "eth0 eth1".
    INTERFACESv4="enxa0cec85db1b2"
    INTERFACESv6=""

- modifier le fichier `/etc/dhcp/dhcpd.conf`. Adapter `DHCPDARGS` :

.. code-block:: console

    ddns-update-style none;
    default-lease-time 86400;
    max-lease-time 604800;
    authoritative;

    allow booting;
    allow bootp;

    subnet 192.168.1.0 netmask 255.255.255.0 {
            range 192.168.1.10 192.168.1.30;
            option subnet-mask 255.255.255.0;
    }

Configuration du boot du Raspberry Pi :

- Configurer les paramètres du kernel, modifier le fichier
  `/srv/nfs/raspi4b/boot/cmdline.txt` :

.. code-block:: console

    console=serial0,115200 console=tty root=/dev/nfs nfsroot=192.168.1.100:/srv/nfs/raspi4b,vers=4.1,proto=tcp rw ip=dhcp rootwait elevator=deadline fsck.repair=yes

- Configurer le montage du rootfs, modifier le fichier
  `/srv/nfs/raspi4b/etc/fstab` :

Supprimer les ligne de montage des partitions de la carte SD (contiennent "UUID").

Autoriser et démarrer les services :

.. code-block:: console

    sudo systemctl enable isc-dhcp-server
    sudo systemctl enable nfs-kernel-server
    sudo systemctl enable tftpd-hpa.service
    sudo systemctl restart isc-dhcp-server
    sudo systemctl restart nfs-kernel-server
    sudo systemctl restart tftpd-hpa.service

Mémo
----

En cas de changement de numéro de série :

- modifier le préfixe et imposer une chaîne fixe ;

ou

- renommer le répertoire ou créer un répertoire `/srv/tftp/<NS>` ;
- renommer le numéro de série ou ajouter une ligne dans le fichier `/etc/fstab` ;


Références
----------
    https://www.raspberrypi.com/documentation/computers/raspberry-pi.html#raspberry-pi-4-boot-eeprom
    https://linuxhit.com/raspberry-pi-pxe-boot-netbooting-a-pi-4-without-an-sd-card/
    https://github.com/garyexplains/examples/blob/master/How%20to%20network%20boot%20a%20Pi%204.md
    https://www.raspberrypi.com/documentation/computers/raspberry-pi.html#raspberry-pi-4-boot-eeprom (§BOOT_ORDER)
    https://hackaday.com/2019/11/11/network-booting-the-pi-4/
