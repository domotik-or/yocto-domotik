FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://wired.nmconnection \
"
#    file://NetworkManager.conf

# Depend on libedit as it has a more friendly license than readline (GPLv3)
DEPENDS += "libedit"

PACKAGECONFIG:remove = "dnsmasq"

# Adjust other compile time options
PACKAGECONFIG:append = " gnutls modemmanager ppp"
RPROVIDES:${PN} = "network-configuration"

FILES:${PN} += "\
 ${sysconfdir}/NetworkManager/system-connections/wired.nmconnection\
"
# ${sysconfdir}/NetworkManager/NetworkManager.conf

do_install:append () {
    install -d ${D}/${sysconfdir}/NetworkManager/system-connections
    # install -m 0600 ${WORKDIR}/NetworkManager.conf ${D}/${sysconfdir}/NetworkManager
    install -m 0600 ${WORKDIR}/wired.nmconnection ${D}/${sysconfdir}/NetworkManager/system-connections
}
