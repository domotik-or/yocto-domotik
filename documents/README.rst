Setup Yocto sur machine hôte
============================

Packages à installer
--------------------

.. code-block:: console

    sudo apt install build-essential chrpath cpio debianutils diffstat file \
    gawk gcc git iputils-ping libacl1 liblz4-tool locales python3 python3-git \
    python3-jinja2 python3-pexpect python3-pip python3-subunit socat texinfo \
    unzip wget xz-utils zstd

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

    umount /dev/sdb?
    bzcat i/mnt/yocto/domotik/yocto-domotik/build/tmp/deploy/images/raspberrypi4-64/domotik-raspberrypi4-64.rootfs.wic.bz2 > /dev/sdb
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
