LICENSE = "CLOSED"

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "can0-socket.service"

SRC_URI:append = " file://can0-socket.service"
FILES:${PN} += "${systemd_unitdir}/system/can0-socket.service"

do_install:append() {
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/can0-socket.service ${D}/${systemd_unitdir}/system
}
