SUMMARY = "A simple program, and an example of how to structure a python project"
AUTHOR = "Franck Barbenoire"
LICENSE = "CLOSED"
# LICENSE = "Apache-2.0"
# LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRCREV = "${AUTOREV}"
PV = "0.1.0+git${SRCPV}"

SRC_URI = "git://github.com/franckinux/python3-domotik.git;branch=master;protocol=https"

# FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://python3-domotik.service \
"

S = "${WORKDIR}/git"

inherit setuptools3 systemd

RDEPENDS:${PN} += " \
    pigpio \
    ${PYTHON_PN}-aiohttp \
    ${PYTHON_PN}-aiomqtt \
    ${PYTHON_PN}-pyserial \
    ${PYTHON_PN}-pyserial-asyncio \
    ${PYTHON_PN}-tomli \
"

SYSTEMD_SERVICE:${PN} = "python3-domotik.service"
FILES:${PN} += "${systemd_unitdir}/system/python3-domotik.service"
FILES:${PN} += "${sysconfdir}/domotik/domotik.toml"

do_install:append() {
  install -d ${D}/${sysconfdir}/domotik
  install -m 0644 ${S}/config.toml ${D}/${sysconfdir}/domotik/domotik.toml
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/python3-domotik.service ${D}/${systemd_unitdir}/system
}
