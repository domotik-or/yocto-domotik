SUMMARY = "Example recipe for using inherit useradd"
DESCRIPTION = "This recipe serves as an example for using features from useradd.bbclass"
SECTION = "examples"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "\
file://domotik-ssh-key.pub \
file://zigbee2mqtt.yaml \
"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

EXCLUDE_FROM_WORLD = "1"

inherit extrausers

# create root user
# root password memo: DxxxxIxxxxVxxx
# obtained with : openssl passwd -5 -salt 00000000 g*4
EXTRA_USERS_PARAMS = "\
    usermod -p '\$5\$00000000\$e0wPq6UDtuqPkpWYJJhbBUCS5b0HIxiF.6ofdHheF/D' root;\
"

# create domotik user
# obtained with : openssl passwd -5 -salt 00000000 hXXXsXXXXhXXe
EXTRA_USERS_PARAMS += "\
    groupadd -g 1000 domotik; \
    groupadd -g 1001 i2c; \
    groupadd -g 1002 spi; \
    useradd -u 1000 -m -d /home/domotik -r -s /bin/sh -g domotik -G dialout,i2c,spi,sudo domotik; \
    usermod -p '\$5\$00000000\$sbvxuw8tAr5xXzgiroRZFw3L57WyhOuD0tojF8UrLQ3' domotik; \
"

do_install () {
    install -d -m 700 ${D}/home/domotik/.ssh
    install -p -m 600 ${WORKDIR}/domotik-ssh-key.pub ${D}/home/domotik/.ssh/authorized_keys

    install -d ${D}/home/domotik/.z2m
    install -m 0644 ${WORKDIR}/zigbee2mqtt.yaml ${D}/home/domotik/.z2m/configuration.yaml
}

ROOTFS_POSTPROCESS_COMMAND += "fix_domotik_permissions; "

fix_domotik_permissions () {
    chown -R domotik:domotik ${D}/home/domotik
    chmod 700 ${D}/home/domotik
}

FILES:${PN} = "/home/domotik/* /home/domotik/.ssh/* /home/domotik/.z2m/*"

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
