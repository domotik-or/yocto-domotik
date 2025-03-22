Setup Yocto sur machine hôte
============================

Packages à installer
--------------------

- gawk ;
- wget ;
- git ;
- diffstat ;
- unzip ;
- texinfo ;
- gcc ;
- build-essential ;
- chrpath ;
- socat ;
- cpio ;
- python3 ;
- python3-pip;
- python3-pexpect ;
- xz-utils ;
- debianutils ;
- iputils-ping ;
- python3-git ;
- python3-jinja2 ;
- libegl1-mesa l;
- ibsdl1.2-dev ;
- python3-subunit ;
- mesa-common-dev ;
- zstd ;
- liblz4-tool ;
- file locales.

.. code-block:: console

    sudo locale-gen en_US.UTF-8

Initialisation de l'environnement de développement
--------------------------------------------------

A faire en début de session.

.. code-block:: console

    cd ~/Documents/yocto  # python virtual environment is activated
    cd build-move-rpi4.scarthgap/
    source ../poky/oe-init-build-env .

Génération de l'image
---------------------

Dans le répertoire de build, c'est à dire après l'initilisation de
l'environnement de développement.

.. code-block:: console

    bitbake domotik

Copie de l'image sur la carte SD
--------------------------------

.. code-block:: console

    umount /dev/sda?
    bzcat /home/franck/Documents/yocto/build-move-rpi4.nanbield/tmp/deploy/images/raspberrypi4-64/sd1-raspberrypi4-64.wic.bz2 > /dev/sda
    sync

Exemple de création d'un fichier .bbappend
------------------------------------------

Eexemple  pour changer la valeur de la variable variable ALTERNATIVE_PRIORITY

.. code-block:: console

    recipetool newappend ../meta-move coreutils

Actions sur machine cible
=========================

Mise à jour de l'eeprom
-----------------------

.. code-block:: console

    sudo update
    sudo upgrade

    domotik@raspberrypi:/lib/firmware/raspberrypi $ sudo rpi-update

	 *** Raspberry Pi firmware updater by Hexxeh, enhanced by AndrewS and Dom
	 *** Performing self-update
	 *** Relaunching after update
	 *** Raspberry Pi firmware updater by Hexxeh, enhanced by AndrewS and Dom
	FW_REV:3608b77cd4557513506dbc098db04938439804aa
	BOOTLOADER_REV:78d08e9763079c6608506e25eefeee5ceb0ceabc
	 *** We're running for the first time
	 *** Backing up files (this will take a few minutes)
	 *** Remove old firmware backup
	 *** Backing up firmware
	 *** Remove old modules backup
	 *** Backing up modules 6.6.74+rpt-rpi-v8
	WANT_32BIT:1 WANT_64BIT:1 WANT_PI4:1 WANT_PI5:0
	##############################################################
	WARNING: This update bumps to rpi-6.12.y linux tree
	See discussions at:
	https://forums.raspberrypi.com/viewtopic.php?t=379745
	##############################################################
	Would you like to proceed? (y/N)
	Downloading bootloader tools

	Downloading bootloader images
	 *** Downloading specific firmware revision (this will take a few minutes)
	  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
									 Dload  Upload   Total   Spent    Left  Speed
	  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
	100  151M  100  151M    0     0   850k      0  0:03:02  0:03:02 --:--:-- 1568k
	BOOTLOADER: up to date
	   CURRENT: Tue 11 Feb 17:00:13 UTC 2025 (1739293213)
		LATEST: Tue 11 Feb 17:00:13 UTC 2025 (1739293213)
	   RELEASE: latest (/usr/lib/firmware/raspberrypi/bootloader-2711/latest)
				Use raspi-config to change the release.

	  VL805_FW: Using bootloader EEPROM
		 VL805: up to date
	   CURRENT: 000138c0
		LATEST: 000138c0
	 *** Updating firmware
	 *** Updating kernel modules
	 *** depmod 6.12.18-v8-16k+
	 *** depmod 6.12.18-v8+
	 *** depmod 6.12.18-v7l+
	 *** depmod 6.12.18+
	 *** depmod 6.12.18-v7+
	 *** Updating VideoCore libraries
	 *** Running ldconfig
	 *** Storing current firmware revision
	 *** Deleting downloaded files
	 *** Syncing changes to disk
	 *** If no errors appeared, your firmware was successfully updated to 3608b77cd4557513506dbc098db04938439804aa
	 *** A reboot is needed to activate the new firmware

Configuration
-------------

Action à réaliser dans la console après reboot

ufw (toujours d'actualité ?) :

.. code-block:: console

    rw
    ufw enable
    ufw allow 22/tcp
    ro

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
