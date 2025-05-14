SUMMARY = "Server"
AUTHOR = "Franck Barbenoire"
LICENSE = "CLOSED"
# LICENSE = "Apache-2.0"
# LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRCREV = "${AUTOREV}"
PV = "0.1.0+git${SRCPV}"

SRC_URI = "git://github.com/domotik-or/server.git;branch=master;protocol=https"

# FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://server.service \
"

S = "${WORKDIR}/git"

inherit python_flit_core systemd

RDEPENDS:${PN} += " \
    ${PYTHON_PN}-aiomqtt \
    ${PYTHON_PN}-aiosmtplib \
    ${PYTHON_PN}-tomli \
"

SYSTEMD_SERVICE:${PN} = "server.service"
FILES:${PN} += "${systemd_unitdir}/system/server.service"
FILES:${PN} += "${sysconfdir}/domotik/server.toml"

do_install:append() {
  install -d ${D}/${sysconfdir}/domotik
  install -m 0644 ${S}/config.toml ${D}/${sysconfdir}/domotik/server.toml
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/server.service ${D}/${systemd_unitdir}/system
}
