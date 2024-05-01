SUMMARY = "A simple program, and an example of how to structure a python project"
AUTHOR = "Franck Barbenoire"
LICENSE = "CLOSED"
# LICENSE = "Apache-2.0"
# LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = " \
    file://setup.py \
    file://domotik/__init__.py \
    file://domotik/main.py \
    file://domotik/other.py \
    "
S = "${WORKDIR}"

inherit setuptools3 systemd

RDEPENDS:${PN} += " \
    ${PYTHON_PN}-aiohttp \
"

SRC_URI += "\
    file://domotik.service \
    "

SYSTEMD_SERVICE:${PN} = "domotik.service"
FILES:${PN} += "${systemd_unitdir}/system/domotik.service"
# FILES:${PN} += "${sysconfdir}/domotik.toml"

do_install:append() {
  # install -d ${D}/${sysconfdir}/domotik
  # install -m 0644 ${S}/config.toml ${D}/${sysconfdir}/domotik.toml
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/domotik.service ${D}/${systemd_unitdir}/system
}
