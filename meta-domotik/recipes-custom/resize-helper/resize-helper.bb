SUMMARY = "Resize the root partition at the first startup"
SECTION = "devel"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-or-later;md5=fed54355545ffd980b814dab4a3b312c"

RDEPENDS:${PN} += "e2fsprogs-resize2fs gptfdisk parted util-linux udev"

inherit systemd

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "file://resize-helper file://resize-helper.service"

S = "${WORKDIR}"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "resize-helper.service"

FILES:${PN} += "${systemd_unitdir}/system/resize-helper.service"
FILES:${PN} += "${sbindir}/resize-helper"

do_install() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${S}/resize-helper.service ${D}${systemd_unitdir}/system

    install -d ${D}${sbindir}
    install -m 0755 ${S}/resize-helper ${D}${sbindir}
}

