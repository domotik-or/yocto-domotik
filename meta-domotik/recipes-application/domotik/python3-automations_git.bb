SUMMARY = "Automation scripts"
AUTHOR = "Franck Barbenoire"
LICENSE = "CLOSED"
# LICENSE = "Apache-2.0"
# LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRCREV = "${AUTOREV}"
PV = "0.1.0+git${SRCPV}"

SRC_URI = "git://github.com/domotik-or/automations.git;branch=master;protocol=https"
SRC_URI[sha256sum] = "9319a980fefc7991cfd5d0f040bb44a1a9549b94b3315fac3fa0e334c081a745"

# FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://automations.env \
    file://automations.service \
    file://automations.toml \
    file://domotik-cleanup \
    file://domotik-snapshot \
    file://cleanup.sh \
    file://snapshot.sh \
"

S = "${WORKDIR}/git"

inherit python_flit_core systemd

RDEPENDS:${PN} += " \
    ${PYTHON_PN}-aiomqtt \
    ${PYTHON_PN}-aiosmtplib \
    ${PYTHON_PN}-aiosqlite \
    ${PYTHON_PN}-dotenv \
"

do_install:append() {
    # configuration files
    install -d ${D}/${sysconfdir}/domotik
    install -m 0644 ${WORKDIR}/automations.env ${D}/${sysconfdir}/domotik
    install -m 0644 ${WORKDIR}/automations.toml ${D}/${sysconfdir}/domotik

    # systemd service
    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/automations.service ${D}/${systemd_unitdir}/system

    # cron files
    install -d ${D}/${sysconfdir}/cron.d
    install -m 644 ${WORKDIR}/domotik-cleanup ${D}/${sysconfdir}/cron.d
    install -m 644 ${WORKDIR}/domotik-snapshot ${D}/${sysconfdir}/cron.d

    # scripts
    install -d ${D}/home/domotik/scripts
    install -m 755 ${WORKDIR}/cleanup.sh ${D}/home/domotik/scripts
    install -m 644 ${WORKDIR}/snapshot.sh ${D}/home/domotik/scripts

    # database
    install -d ${D}/home/domotik/database
}

SYSTEMD_SERVICE:${PN} = "automations.service"
FILES:${PN} += "${systemd_unitdir}/system/automations.service"
FILES:${PN} += "${sysconfdir}/domotik/automations.env"
FILES:${PN} += "${sysconfdir}/domotik/automations.toml"
FILES:${PN} += "${sysconfdir}/cron.d/domotik-cleanup"
FILES:${PN} += "${sysconfdir}/cron.d/domotik-snapshot"
FILES:${PN} += "/home/domotik/database"
FILES:${PN} += "/home/domotik/scripts"
FILES:${PN} += "/home/domotik/scripts/cleanup.sh"
FILES:${PN} += "/home/domotik/scripts/snapshot.sh"
