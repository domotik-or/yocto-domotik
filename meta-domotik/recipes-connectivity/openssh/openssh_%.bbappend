FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://authentication.conf \
    "

do_install:append () {
    install -d ${D}${sysconfdir}/ssh/sshd_config.d
    install -m 0660 ${WORKDIR}/authentication.conf ${D}${sysconfdir}/ssh/sshd_config.d
}
