# From: https://stackoverflow.com/questions/68676864/install-canopen-package-in-yocto
SUMMARY = "Linux CANOpen tools"
DESCRIPTION = "Linux CANOpen Protocol Stack Tools"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "https://github.com/CANopenNode/CANopenLinux.git"
SRCREV = "b99ab16ad570437b34a34a217f76b117f07a079e"

S = "${WORKDIR}/git"

do_compile:prepend() {
    cd ${S}
    git submodule init
    git submodule update
}

do_compile() {
    cd ${S}/cocomm
    make
    cd ${S}/canopencgi
    make
}

do_install(){
    install -d ${D}${bindir}
    install -m 0755 ${S}/cocomm/cocomm ${D}${bindir}
    install -m 0755 ${S}/canopencgi/canopen.cgi ${D}${bindir}
}

FILES_${PN} += "${bindir}/*"
