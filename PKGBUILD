pkgname="pipIDE"
pkgver="0.1.0"
pkgrel="1"
pkgdesc="A simple IDE for Java"
archs="i686 x86_64"
depends=("npm" "jdk-openjdk" "maven")

build() {
  cd "$srcdir/$pkgname-$pkgver/react-frontend/ping-react-frontend"
  npm install
  npm run build
}