SUMMARY = "Input/output for home automation"
AUTHOR = "Franck Barbenoire"
LICENSE = "CLOSED"
# LICENSE = "Apache-2.0"
# LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRCREV = "${AUTOREV}"
PV = "0.1.0+git${SRCPV}"

SRC_URI = "git://github.com/domotik-or/domio.git;branch=master;protocol=https"
SRC_URI[sha256sum] = "a0a92f64133ca804a485a07dddc262a5fdcd389829f90d5400c0d562c13e69a1"

# FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://domio.service \
    file://domio.toml \
"

S = "${WORKDIR}/git"

inherit python_flit_core systemd

RDEPENDS:${PN} += " \
    ${PYTHON_PN}-aiohttp \
    ${PYTHON_PN}-aiomqtt \
    ${PYTHON_PN}-can \
    ${PYTHON_PN}-gpiod \
    ${PYTHON_PN}-pyserial \
    ${PYTHON_PN}-smbus2 \
"

SYSTEMD_SERVICE:${PN} = "domio.service"
FILES:${PN} += "${systemd_unitdir}/system/domio.service"
FILES:${PN} += "${sysconfdir}/domotik/domio.toml"

do_install:append() {
  install -d ${D}/${sysconfdir}/domotik
  install -m 0644 ${WORKDIR}/domio.toml ${D}/${sysconfdir}/domotik/domio.toml
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/domio.service ${D}/${systemd_unitdir}/system
}
