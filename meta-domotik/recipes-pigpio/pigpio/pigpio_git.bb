SUMMARY = "pigpio is a C library for the Raspberry which allows control of the \
General Purpose Input Outputs (GPIO)"
DESCRIPTION = "pigpio is a C library for the Raspberry which allows control of \
the General Purpose Input Outputs (GPIO)"

AUTHOR = "<komar@evologics.de> <franck.barbenoire@boble.tech>"

# FIXME: find out right license
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://UNLICENCE;md5=61287f92700ec1bdf13bc86d8228cd13"

SECTION = "utils"

inherit lib_package systemd

SRCREV = "c33738a320a3e28824af7807edafda440952c05d"
SRC_URI = "git://github.com/joan2937/pigpio.git;branch=master;protocol=https"
SRC_URI += " file://001-no-python.patch"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += "CC='${CC}'"
EXTRA_OEMAKE += "CROSS_PREFIX=${TARGET_PREFIX}"
# we don't need to strip, it will be stripped by build system
EXTRA_OEMAKE += "STRIP=echo"
EXTRA_OEMAKE += "PYINSTALLARGS='--root=$(DESTDIR) --prefix=${prefix}'"
TARGET_CC_ARCH += "${LDFLAGS}"

# gpio package will be empty with depends from all packages
ALLOW_EMPTY:${PN} = "1"
ALLOW_EMPTY:${PN}-dbg = "0"
ALLOW_EMPTY:${PN}-dev = "0"

FILES:${PN}-bin-tools  = "${bindir}/pigs"
FILES:${PN}-bin-tools += "${bindir}/pig2vcd"

FILES:${PN}-bin-pigpiod    = "${bindir}/pigpiod"
RDEPENDS:${PN}-bin-pigpiod = "lib${PN}"

# *-bin package will be empty with depends from all *-bin-* packages
RDEPENDS:${PN}-bin = " ${PN}-bin-pigpiod ${PN}-bin-tools"
ALLOW_EMPTY:${PN}-bin = "1"

FILES:lib${PN}  = "${libdir}/lib${PN}.so.*"
FILES:lib${PN}  =+ "/opt/${PN}/cgi"

FILES:lib${PN}_if  = "${libdir}/lib${PN}_if.so.*"
FILES:lib${PN}_if2 = "${libdir}/lib${PN}_if2.so.*"

FILES:${PN}-dev = "${includedir}/*.h"
FILES:${PN}-dev += "${libdir}/lib${PN}*.so"
FILES:${PN}-doc = "${mandir}"

PACKAGES =+ " ${PN}-bin-pigpiod ${PN}-bin-tools lib${PN} lib${PN}_if lib${PN}_if2"

do_install() {
    oe_runmake install DESTDIR=${D} prefix=${prefix} mandir=${mandir}
}

# systemd
SYSTEMD_PACKAGES = "${PN}-bin-pigpiod"
SYSTEMD_SERVICE:${PN}-bin-pigpiod = "pigpiod.service"
FILES:${PN}-bin-pigpiod += "${systemd_unitdir}/system/pigpiod.service"
do_install:append() {
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${S}/util/pigpiod.service ${D}/${systemd_unitdir}/system
  install -m 755 -d ${D}/opt/${PN}/cgi
}
