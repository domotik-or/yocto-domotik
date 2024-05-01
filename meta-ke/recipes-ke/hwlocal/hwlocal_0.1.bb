DESCRIPTION = "This is a simple Hello World recipe - uses a local source file"
HOMEPAGE = "https://kickstartembedded.com"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade69"
SRC_URI = "file://hello-world-local.c"
S = "${WORKDIR}"
do_compile() {
${CC} ${LDFLAGS} hello-world-local.c -o hello-world-local
}
do_install() {
install -d ${D}${bindir}
install -m 0755 hello-world-local ${D}${bindir}
}
