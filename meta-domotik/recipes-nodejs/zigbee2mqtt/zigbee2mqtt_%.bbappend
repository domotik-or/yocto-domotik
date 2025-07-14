inherit systemd

FILESEXTRAPATHS:prepend := "${THISDIR}/zigbee2mqtt:"

# S = "${WORKDIR}/npm"

INSANE_SKIP:${PN} += "already-stripped arch file-rdeps"
INSANE_SKIP:${PN}-serialport-bindings-cpp += "already-stripped arch"
INSANE_SKIP:${PN}-serialport-bindings-cpp-debug += "already-stripped arch"

SRC_URI += "\
    file://zigbee2mqtt.service \
"
SYSTEMD_SERVICE:${PN} = "zigbee2mqtt.service"
FILES:${PN} += "${systemd_unitdir}/system/zigbee2mqtt.service"

do_install:append() {
    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/zigbee2mqtt.service ${D}/${systemd_unitdir}/system
}
