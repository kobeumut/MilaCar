# MilaCar: Kurulum ve Araç İçi Kullanım Kılavuzu

MilaCar, açık kaynaklı [Fermata Media Player](https://github.com/AndreyPavlenko/Fermata) altyapısı üzerine kurulmuş, güvenlik açısından incelenmiş ve araç içi kullanım için düzenlenmiş bir Android Auto medya oynatıcısıdır. Kaynak kodu: https://github.com/kobeumut/MilaCar

| | |
|---|---|
| Dosya | `milacar-3.0.2-arm64.apk` |
| Paket adı | `com.kobeumut.milacar.auto` (Fermata ile yan yana kurulabilir) |
| Sürüm | 3.0.2 (Fermata 2.0.2 / `45c12a2` tabanlı) |
| Mimari | arm64-v8a (2017'den sonra çıkmış neredeyse tüm Android telefonlar) |
| En düşük Android sürümü | Android 9 |
| İmza sertifikası SHA-256 | `a1f637cf58e1fe58b23ad785bc31cb52a2f62cd332287052eed4f3ed6b35c14d` |

MilaCar, telefondaki ya da ağdaki (SMB/SFTP) müzik ve video klasörlerini çalar. Android Auto ekranında kendi tam arayüzüyle açılabilir. Araç durduğunda video oynatabilir, ayrıca telefon ekranını araca yansıtabilir.

## 1. Telefona kurulum

1. `fermata-car-2.0.2-arm64.apk` dosyasını telefona kopyalayın (USB, Google Drive, Telegram vb.).
2. Dosyaya dokunun. Android "bilinmeyen uygulamaları yükleme" izni isterse, dosyayı açtığınız uygulamaya (Dosyalar, Chrome vb.) bu izni verin.
3. Play Protect uyarı verirse **Ayrıntılar → Yine de yükle** seçin. Uygulama Play Store'dan gelmediği için bu uyarı normaldir.
4. **MilaCar** uygulamasını telefonda bir kez açın ve izinleri verin:
   - **Müzik ve ses / Dosyalar ve medya**: klasörlerinizi okumak için gereklidir.
   - **Bildirimler**: çalan parçanın bildirimde görünmesi için.
   - **Tüm dosyalara erişim** sorulursa verin. Klasör tarayıcısı için gereklidir.
5. **Klasörler** sekmesinde **+ (Klasör ekle)** ile müzik klasörünüzü seçin. Ağdaki bir paylaşımı da (SMB veya SFTP) ekleyebilirsiniz.

> **Pil optimizasyonu:** Ayarlar → Uygulamalar → MilaCar → Pil → **Kısıtlanmamış** seçin. Aksi halde bazı telefonlar uzun yolculukta çalmayı durdurabilir.

## 2. Android Auto'da görünmesini sağlama (tek seferlik)

Android Auto, Play Store dışından kurulan uygulamaları varsayılan olarak gizler. Görünmeleri için:

1. Telefonda **Android Auto ayarlarını** açın (Ayarlar → Bağlı cihazlar → Bağlantı tercihleri → Android Auto).
2. En alttaki **Sürüm** satırına art arda **10 kez** dokunun ve "Geliştirici ayarlarına izin ver" sorusunu onaylayın.
3. Sağ üstteki **⋮ menüsü → Geliştirici ayarları** bölümüne girin.
4. **Bilinmeyen kaynaklar** seçeneğini **açın**.
5. **Tam arayüzü etkinleştirin.** "Bilinmeyen kaynaklar" yalnızca **MilaCar Media Service**'i (standart müzik ekranı: klasörler, favoriler, listeler) gösterir. YouTube, tarayıcı ve video içeren tam **MilaCar** uygulamasını Android Auto yalnızca Google Play'den kurulan uygulamalarda gösterir. Yapılacak şey telefonunuzun Android sürümüne bağlıdır (Ayarlar → Telefon hakkında → Android sürümü):
   - **Android 13 ve altı:** MilaCar'ı telefonda açın ve **"Tam uygulamayı Android Auto'da göster"** sorusuna **Tamam** deyin. Soruyu kaçırdıysanız: **Ayarlar → Arayüz → Android Auto → Tam uygulamayı Android Auto'da göster**. Android'in kurulum ekranında **Güncelle/Yükle**'yi onaylayın. Uygulama aynı APK ile yeniden kurulur; internetten hiçbir şey indirilmez, ayarlarınız korunur. Bazı üreticilerin kurulum ekranları (ör. Xiaomi/MIUI) bunu yok sayarsa bilgisayardan bir kez şu komutla kurun: `adb install -r -i com.android.vending milacar-3.0.2-arm64.apk`
   - **Android 14 ve üzeri:** Google bu yolu kapattı. Uygulamayı silip yeniden kurmak, KingInstaller, AAAD ya da `adb install -i` **işe yaramaz**. Tam arayüz için şunlardan biri gerekir:
     - **Root + LSPosed:** Magisk'te Zygisk'i açın, LSPosed modülünü kurun, LSPosed'de **MilaCar**'ı etkinleştirip telefonu yeniden başlatın. MilaCar'ın içindeki Android Auto modülü devreye girer.
     - **Geliştirici modunda kablosuz adaptör:** [AAWireless](https://www.aawireless.io/) (cihaz ayarlarında geliştirici modu açık) veya [aa-proxy-rs](https://github.com/aa-proxy/aa-proxy-rs) (MITM ve geliştirici modu açık). Adaptör telefona kendini geliştirici başlık ünitesi olarak tanıtır, bu yüzden Android Auto tüm uygulamaları gösterir.
   - Bunlar yoksa **MilaCar Media Service** ile müzik dinlemeye devam edebilirsiniz.
6. Telefonu araçtan çıkarıp yeniden takın (ya da kablosuz bağlantıyı kesip yeniden bağlayın).
7. Araç ekranındaki Android Auto uygulama listesinde **MilaCar** simgesi görünür. Görünmüyorsa: Android Auto ayarları → **Başlatıcıyı özelleştir** bölümünden MilaCar'ı listeye ekleyin.

Araç ekranında üç simge görebilirsiniz:

| Simge | Ne işe yarar |
|---|---|
| **MilaCar** | Uygulamanın tam arayüzü araç ekranında açılır: klasörler, favoriler, video, ayarlar. Önerilen kullanım budur. |
| **MilaCar Media Service** | Android Auto'nun standart müzik ekranı. Basit ve sürüş sırasında en güvenli görünümdür. |
| **MilaCar Mirror** | Telefon ekranını araca yansıtır. Ek kurulum gerektirir (bkz. Bölüm 5). |

## 3. Araçta kullanım

- **Çalma:** Klasörler → klasör → parça. Kontrol paneli (oynat/duraklat, ileri/geri, sarma çubuğu) ekranın altında görünür.
- **Kaldığı yerden devam:** MilaCar her klasör için son çalınan parçayı ve konumu hatırlar. Araca bağlanınca son çalınan devam eder.
- **Direksiyon tuşları ve araç düğmeleri:** Oynat/duraklat ve ileri/geri tuşları standart medya kontrolleri olarak çalışır.
- **Favoriler ve listeler:** Bir parçaya uzun basın → **Favorilere ekle**. Yolda kullanmak için en hızlı yöntem budur.
- **Sesli kontrol:** Ayarlar → Sesli kontrol bölümünden açılır. İsterseniz ekranda bir **Kayan düğme** gösterilir ya da uzun basmayla etkinleşir.
- **Video:** Güvenlik nedeniyle yalnızca araç dururken izleyin. Medya motoru olarak VLC seçilirse altyazı ve ses kanalı seçimi de kullanılabilir.

### Araç teması (MilaCar'a özel)

Android Auto ekranı varsayılan olarak yeni **Araç (yüksek kontrast)** temasıyla açılır:

- Saf siyah arka plan: gece göz kamaştırmaz, OLED ekranlarda enerji tasarrufu sağlar.
- Beyaz yazılar ve tek bir **amber vurgu rengi**: güneş altında okunur, çalan parça ve ilerleme çubuğu bir bakışta seçilir.
- **Daha büyük kontrol düğmeleri** (70 dp yerine 84 dp): yolda isabetli dokunmak kolaylaşır.
- Daha yuvarlak köşeler ve belirgin seçili satır.

Değiştirmek ya da ince ayar yapmak için: **Ayarlar → Android Auto** bölümüne girin. Buradaki ayarlar yalnızca araç ekranını etkiler, telefon arayüzü ayrı ayarlanır.
- **Uygulama teması**: Araç (yüksek kontrast) / Siyah / Koyu …
- **Kontrol paneli boyutu**, **Metin ve simge boyutu**, **Gezinme çubuğu boyutu**: ekranınız küçükse küçültün, büyükse büyütün.
- **Gezinme çubuğu konumu**: sürücü tarafına (Solda) almak erişimi kolaylaştırır.
- **Tam ekran modu** / **Çubukları gizle**: içerik için daha fazla alan açar.

> Tema değişikliği araç ekranında hemen uygulanmazsa uygulamayı kapatıp açın ("Lütfen uygulamayı yeniden başlatın" uyarısı normaldir).

## 4. Sorun giderme

| Sorun | Çözüm |
|---|---|
| Yalnızca **MilaCar Media Service** görünüyor, YouTube ve diğer özellikler yok | Media Service standart müzik ekranıdır, YouTube/tarayıcı orada bulunmaz. Android 13 ve altında Bölüm 2, adım 5'i uygulayın. **Android 14 ve üzerinde** silip yeniden kurmak işe yaramaz; root + LSPosed ya da geliştirici modunda bir adaptör gerekir. |
| MilaCar araç ekranında görünmüyor | Bölüm 2'deki **Bilinmeyen kaynaklar** açık mı kontrol edin. Telefonu yeniden bağlayın. **Başlatıcıyı özelleştir** listesine bakın. |
| Açılıyor ama boş ekran | MilaCar'ı önce telefonda bir kez açıp izinleri verin, sonra araçta yeniden açın. |
| Klasörler boş | Telefonda MilaCar → Ayarlar → Uygulama izinleri bölümünde dosya/medya izni verin, klasörü yeniden ekleyin. |
| Çalma yolda kesiliyor | Pil optimizasyonunu **Kısıtlanmamış** yapın (Bölüm 1). |
| Android Auto güncellemesinden sonra kayboldu | Geliştirici ayarlarındaki **Bilinmeyen kaynaklar** bazen kapanır. Yeniden açın. |
| Orijinal Fermata da kurulu | Sorun değil. MilaCar'ın paket adı farklıdır, ikisi ayrı uygulama olarak görünür. Karışıklığı önlemek için birini kaldırabilirsiniz. |

## 5. İleri düzey ve isteğe bağlı özellikler

Bunların hiçbiri normal kullanım için gerekli değildir. Hepsi varsayılan olarak kapalıdır.

- **MilaCar Mirror (ekran yansıtma):** Telefon ekranını araca yansıtır. Gerekenler: Android **Erişilebilirlik** ayarlarından MilaCar hizmetini açmak (dokunmaları telefona iletmek için) ve ilk açılışta ekran kaydı iznini onaylamak.
- **Root / LSPosed:** Root izni verilirse yansıtma izni otomatik ayarlanır. LSPosed modülü etkinleştirilirse Android Auto'nun "Play Store'dan mı kuruldu" denetimi atlanır. Android 14 ve üzerinde, adaptör yoksa tam arayüz için tek yol budur. Bu modül etkinken Android Auto içinde **tüm** uygulamalar Play'den kurulmuş gibi görünür.

## 6. Güvenlik incelemesi: ne bulundu, ne değişti

Kaynak kodun tamamı (Java, manifest dosyaları, build betikleri, CI tanımları ve ikili dosyalar) incelendi. **Zararlı yazılım bulunmadı:** reklam SDK'sı, analitik/izleme, kripto madencisi, gizli sunucu, şifrelenmiş yük veya internetten kod indirme yok. Cihaz kimliği, rehber, SMS ya da arama kaydı okunmuyor. Paketteki ikili dosyalar da kontrol edildi. `aauto.aar` yalnızca Google Android Auto SDK sınıflarını içeriyor. Xposed jar'ı yalnızca derleme zamanı stub'larından oluşuyor ve APK'ya girmiyor. `gradle-wrapper.jar` resmi Gradle sağlama toplamıyla birebir eşleşiyor.

Bununla birlikte Fermata'da **kötü niyetli olmayan ama ciddi güvenlik açıkları** ve gereksiz bazı özellikler vardı. MilaCar'da bunlar düzeltildi ya da kaldırıldı:

| # | Fermata'daki bulgu | MilaCar'da |
|---|---|---|
| 1 | Uygulamanın kendi HTTPS istemcisi **hiçbir sertifikayı doğrulamıyordu** (trust-all). Aynı Wi-Fi'daki biri bağlantıları dinleyip değiştirebilirdi. | **Düzeltildi:** sistem sertifika deposu ve alan adı (hostname) doğrulaması zorunlu. |
| 2 | Dışa açık `ContentProvider`, telefondaki **herhangi bir uygulamanın** uygulamanın özel dosyalarını okumasına izin veriyordu. SMB/SFTP şifreleri de bu dosyalarda tutuluyor. | **Düzeltildi:** yalnızca resim dosyaları ve kapak görseli önbelleği okunabilir. Uygulama verileri erişime kapalı. |
| 3 | Otomatik güncelleme, orijinal geliştiricinin GitHub'ından APK indirip kendini **Play Store'dan kurulmuş gibi** gösteriyordu (doğrulamasız bağlantı üzerinden). | **Kaldırıldı**, uygulama silme izni de kaldırıldı. Kurma izni yalnızca uygulamanın telefonda kurulu kendi APK'sını Android Auto için yeniden kurmakta kullanılıyor; internetten bir şey indirilmiyor. |
| 4 | Varsayılan imza anahtarı herkesin bildiği bir paroladan üretiliyordu. | MilaCar APK'sı **size özel, rastgele üretilmiş** bir anahtarla imzalandı. |
| 5 | Tarayıcı eklentisi, DRM isteyen herhangi bir web sitesine **mikrofon ve kamera** veriyordu. Araç modunda ise sormadan veriyordu. | **Düzeltildi:** kamera web sayfalarına hiç verilmiyor. Mikrofon yalnızca siz telefonda izin verdiyseniz veriliyor. Kamera izni APK'dan kaldırıldı. |
| 6 | Root'lu cihazda dosya silme komutu, dosya adındaki `'` karakteriyle komut enjeksiyonuna açıktı. | **Düzeltildi:** dosya yolu kabuk için doğru biçimde tırnaklanıyor. |
| 7 | Ayarlar, kayıtlı şifrelerle birlikte Google bulut yedeğine gidiyordu. | **Bulut yedeği kapatıldı.** Ayarları başka bir telefona taşımak için Ayarlar → **Tercihleri dışa dosyaya aktar** / **Tercihleri dosyadan içe aktar** kullanılabilir. |
| 8 | Kullanılmayan `READ_PHONE_STATE` izni. | **Kaldırıldı.** |
| 9 | Araçta gereksiz ve veri gönderen ya da Google Play Services'e bağımlı modüller: ChatGPT sohbeti (OpenAI'ye metin gönderir), POI/radar uyarısı (konumu üçüncü tarafa gönderir), ML Kit çeviri, Chromecast, Whisper altyazı üretimi, OpusMT çeviri. | **Derlemeden çıkarıldı.** APK 158 MB'tan 61 MB'a düştü. |
| 10 | Uygulama çöktüğünde, onay alınarak çökme kaydı Fermata'nın geliştiricisine e-postayla gönderilebiliyordu. | **Kaldırıldı.** MilaCar çökme raporu göndermez. |

Bilinen, bilerek dokunulmayan noktalar:
- Tarayıcı ve YouTube eklentileri doğal olarak açtığınız sitelere bağlanır. Bağış bağlantıları yalnızca dokunursanız açılır.

## 7. Güncelleme

Otomatik güncelleme kaldırıldı. Yeni sürümler https://github.com/kobeumut/MilaCar üzerinden derlenir. Yeni APK **aynı anahtarla** imzalanırsa mevcut kurulumun üzerine, ayarlar korunarak kurulur. İmza anahtarı depoda değildir, derleme makinesinde saklanır. Kaybolursa güncelleme yapılamaz, uygulamayı kaldırıp yeniden kurmanız gerekir.

## 8. Kaynaktan derleme

Ayrıntılar için depodaki `README.md` dosyasına bakın. Kısaca:

```bash
git clone https://github.com/kobeumut/MilaCar.git && cd MilaCar
export JAVA_HOME=<JDK 21>          # JDK 17.0.7'nin javac'ı bu projede çöküyor
export ANDROID_SDK_ROOT=<Android SDK yolu> NO_GS=true
# local.properties: storeFile, keyAlias, keyPassword, storePassword (kendi imza anahtarınız)
./gradlew fermata:packageAutoReleaseUniversalApk -PABI=arm64-v8a
# çıktı: fermata/build/outputs/apk_from_bundle/autoRelease/milacar-3.0.2-auto-release-universal.apk
```
