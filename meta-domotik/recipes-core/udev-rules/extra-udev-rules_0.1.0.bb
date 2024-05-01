SUMMARY = "Additional udev files"
LICENSE = "CLOSED"

SRC_URI = "\
    file://60-i2c-tools.rules \
    file://61-usb-serial.rules \
    file://70-persistent-net.rules \
"

do_install () {
    install -d ${D}/${sysconfdir}/udev/rules.d
    install -m 0666 ${WORKDIR}/60-i2c-tools.rules ${D}/${sysconfdir}/udev/rules.d
    install -m 0666 ${WORKDIR}/61-usb-serial.rules ${D}/${sysconfdir}/udev/rules.d
    install -m 0666 ${WORKDIR}/70-persistent-net.rules ${D}/${sysconfdir}/udev/rules.d
}

FILES:${PN} = "${sysconfdir}/udev"
PACKAGES = "${PN}"
RDEPENDS:${PN} = "udev"
