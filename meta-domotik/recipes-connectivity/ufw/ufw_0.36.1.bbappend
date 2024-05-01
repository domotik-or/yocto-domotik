FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://0001-user-rules.patch;subdir=ufw-0.36.1 \
    file://0002-enabled-at-boot.patch \
    file://0003-disable-logs.patch \
    "
