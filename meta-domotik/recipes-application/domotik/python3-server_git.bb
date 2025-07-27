SUMMARY = "Server for displaying graphs"
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
    file://server.toml \
"

S = "${WORKDIR}/git"

inherit python_flit_core systemd

RDEPENDS:${PN} += " \
    ${PYTHON_PN}-aiohttp \
    ${PYTHON_PN}-aiohttp-cors \
    ${PYTHON_PN}-aiohttp-jinja2 \
    ${PYTHON_PN}-aiosqlite \
    ${PYTHON_PN}-dotenv \
    ${PYTHON_PN}-matplotlib \
    ${PYTHON_PN}-qbstyles \
    ${PYTHON_PN}-pytz \
"

SYSTEMD_SERVICE:${PN} = "server.service"
FILES:${PN} += "${systemd_unitdir}/system/server.service"
FILES:${PN} += "${sysconfdir}/domotik/server.env"
FILES:${PN} += "${sysconfdir}/domotik/server.toml"

do_install:append() {
  install -d ${D}/${sysconfdir}/domotik
  install -m 0644 ${WORKDIR}/server.toml ${D}/${sysconfdir}/domotik/server.toml
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/server.service ${D}/${systemd_unitdir}/system
}
