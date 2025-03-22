LICENSE = "CLOSED"

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "journal-cleanup.service"

SRC_URI:append = " file://journal-cleanup.service "
FILES:${PN} += "${systemd_unitdir}/system/journal-cleanup.service"

do_install:append() {
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/journal-cleanup.service ${D}/${systemd_unitdir}/system
}
