FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://Wired_connection_1.nmconnection \
"

# Depend on libedit as it has a more friendly license than readline (GPLv3)
DEPENDS += "libedit"

PACKAGECONFIG:remove = "dnsmasq"

# Adjust other compile time options
PACKAGECONFIG:append = " gnutls modemmanager ppp"
RPROVIDES:${PN} = "network-configuration"

FILES:${PN} += "${sysconfdir}/NetworkManager/system-connections/Wired_connection_1.nmconnection"

do_install:append () {
    install -d ${D}/${sysconfdir}/NetworkManager/system-connections
    install -m 0600 ${WORKDIR}/Wired_connection_1.nmconnection ${D}/${sysconfdir}/NetworkManager/system-connections
}
