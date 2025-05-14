SUMMARY = "Automation scripts"
AUTHOR = "Franck Barbenoire"
LICENSE = "CLOSED"
# LICENSE = "Apache-2.0"
# LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRCREV = "${AUTOREV}"
PV = "0.1.0+git${SRCPV}"

SRC_URI = "git://github.com/domotik-or/automations.git;branch=master;protocol=https"

# FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://automations.service \
"

S = "${WORKDIR}/git"

inherit python_flit_core systemd

RDEPENDS:${PN} += " \
    ${PYTHON_PN}-aiomqtt \
    ${PYTHON_PN}-aiosmtplib \
"

SYSTEMD_SERVICE:${PN} = "automations.service"
FILES:${PN} += "${systemd_unitdir}/system/automations.service"
FILES:${PN} += "${sysconfdir}/domotik/automations.toml"

do_install:append() {
  install -d ${D}/${sysconfdir}/domotik
  install -m 0644 ${S}/config.toml ${D}/${sysconfdir}/domotik/automations.toml
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/automations.service ${D}/${systemd_unitdir}/system
}
