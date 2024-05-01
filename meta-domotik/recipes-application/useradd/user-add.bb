SUMMARY = "Example recipe for using inherit useradd"
DESCRIPTION = "This recipe serves as an example for using features from useradd.bbclass"
SECTION = "examples"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://domotik-ssh-key.pub \
          "

S = "${WORKDIR}"

EXCLUDE_FROM_WORLD = "1"

inherit useradd

USERADD_PACKAGES = "${PN}"

GROUPADD_PARAM:${PN} = "-g 1000 domotik; -g 1001 i2c; -g 1002 spi; -g 1003 can;"
# obtained with : openssl passwd -5 -salt 00000000 XXXXXXXXXXX
USERADD_PARAM:${PN} = "-p '\$5\$00000000\$EexRKUwOMtYkYl6TNNON2KP4j3mogiFKoIo26CPMy92' -u 1000 -d /home/domotik -r -s /bin/sh -g domotik -G dialout,i2c,spi,can domotik"


do_install () {
	install -d -m 700 ${D}/home/domotik
	install -d -m 700 ${D}/home/domotik/.ssh
	install -p -m 600 ${S}/domotik-ssh-key.pub ${D}/home/domotik/.ssh/authorized_keys
	chown -R domotik ${D}/home/domotik
	chgrp -R domotik ${D}/home/domotik
}

FILES:${PN} = "/home/domotik/* /home/domotik/.ssh/*"

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
