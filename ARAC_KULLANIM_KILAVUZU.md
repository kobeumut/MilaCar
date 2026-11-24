# Fermata Auto (Araç Sürümü): Kurulum ve Kullanım Kılavuzu

Bu APK, açık kaynaklı [Fermata Media Player](https://github.com/AndreyPavlenko/Fermata) uygulamasının güvenlik açısından incelenmiş ve araç içi kullanım için düzenlenmiş bir derlemesidir.

| | |
|---|---|
| Dosya | `fermata-car-2.0.2-arm64.apk` |
| Paket adı | `me.aap.fermata.auto.car` (orijinal Fermata ile yan yana kurulabilir) |
| Sürüm | 2.0.2 (upstream `45c12a2`) |
| Mimari | arm64-v8a (2017'den sonra çıkmış neredeyse tüm Android telefonlar) |
| En düşük Android sürümü | Android 9 |
| İmza sertifikası SHA-256 | `a1f637cf58e1fe58b23ad785bc31cb52a2f62cd332287052eed4f3ed6b35c14d` |

Fermata, telefondaki ya da ağdaki (SMB/SFTP) müzik ve video klasörlerini çalan bir oynatıcıdır. Android Auto ekranında kendi tam arayüzüyle açılabilir. Araç durduğunda video oynatabilir, ayrıca telefon ekranını araca yansıtabilir.

## 1. Telefona kurulum

1. `fermata-car-2.0.2-arm64.apk` dosyasını telefona kopyalayın (USB, Google Drive, Telegram vb.).
2. Dosyaya dokunun. Android "bilinmeyen uygulamaları yükleme" izni isterse, dosyayı açtığınız uygulamaya (Dosyalar, Chrome vb.) bu izni verin.
3. Play Protect uyarı verirse **Ayrıntılar → Yine de yükle** seçin. Uygulama Play Store'dan gelmediği için bu uyarı normaldir.
4. **Fermata Auto** uygulamasını telefonda bir kez açın ve izinleri verin:
   - **Müzik ve ses / Dosyalar ve medya**: klasörlerinizi okumak için gereklidir.
   - **Bildirimler**: çalan parçanın bildirimde görünmesi için.
   - **Tüm dosyalara erişim** sorulursa verin. Klasör tarayıcısı için gereklidir.
5. **Klasörler** sekmesinde **+ (Klasör ekle)** ile müzik klasörünüzü seçin. Ağdaki bir paylaşımı da (SMB veya SFTP) ekleyebilirsiniz.

> **Pil optimizasyonu:** Ayarlar → Uygulamalar → Fermata Auto → Pil → **Kısıtlanmamış** seçin. Aksi halde bazı telefonlar uzun yolculukta çalmayı durdurabilir.

## 2. Android Auto'da görünmesini sağlama (tek seferlik)

Android Auto, Play Store dışından kurulan uygulamaları varsayılan olarak gizler. Görünmeleri için:

1. Telefonda **Android Auto ayarlarını** açın (Ayarlar → Bağlı cihazlar → Bağlantı tercihleri → Android Auto).
2. En alttaki **Sürüm** satırına art arda **10 kez** dokunun ve "Geliştirici ayarlarına izin ver" sorusunu onaylayın.
3. Sağ üstteki **⋮ menüsü → Geliştirici ayarları** bölümüne girin.
4. **Bilinmeyen kaynaklar** seçeneğini **açın**.
5. Telefonu araçtan çıkarıp yeniden takın (ya da kablosuz bağlantıyı kesip yeniden bağlayın).
6. Araç ekranındaki Android Auto uygulama listesinde **Fermata Auto** simgesi görünür. Görünmüyorsa: Android Auto ayarları → **Başlatıcıyı özelleştir** bölümünden Fermata'yı listeye ekleyin.

Araç ekranında üç simge görebilirsiniz:

| Simge | Ne işe yarar |
|---|---|
| **Fermata Auto** | Uygulamanın tam arayüzü araç ekranında açılır: klasörler, favoriler, video, ayarlar. Önerilen kullanım budur. |
| **Fermata Media Service** | Android Auto'nun standart müzik ekranı. Basit ve sürüş sırasında en güvenli görünümdür. |
| **Fermata Mirror** | Telefon ekranını araca yansıtır. Ek kurulum gerektirir (bkz. Bölüm 5). |

## 3. Araçta kullanım

- **Çalma:** Klasörler → klasör → parça. Kontrol paneli (oynat/duraklat, ileri/geri, sarma çubuğu) ekranın altında görünür.
- **Kaldığı yerden devam:** Fermata her klasör için son çalınan parçayı ve konumu hatırlar. Araca bağlanınca son çalınan devam eder.
- **Direksiyon tuşları ve araç düğmeleri:** Oynat/duraklat ve ileri/geri tuşları standart medya kontrolleri olarak çalışır.
- **Favoriler ve listeler:** Bir parçaya uzun basın → **Favorilere ekle**. Yolda kullanmak için en hızlı yöntem budur.
- **Sesli kontrol:** Ayarlar → Sesli kontrol bölümünden açılır. İsterseniz ekranda bir **Kayan düğme** gösterilir ya da uzun basmayla etkinleşir.
- **Video:** Güvenlik nedeniyle yalnızca araç dururken izleyin. Medya motoru olarak VLC seçilirse altyazı ve ses kanalı seçimi de kullanılabilir.

### Araç teması (bu sürümde yeni)

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
| Fermata araç ekranında görünmüyor | Bölüm 2'deki **Bilinmeyen kaynaklar** açık mı kontrol edin. Telefonu yeniden bağlayın. **Başlatıcıyı özelleştir** listesine bakın. |
| Açılıyor ama boş ekran | Fermata'yı önce telefonda bir kez açıp izinleri verin, sonra araçta yeniden açın. |
| Klasörler boş | Telefonda Fermata → Ayarlar → Uygulama izinleri bölümünde dosya/medya izni verin, klasörü yeniden ekleyin. |
| Çalma yolda kesiliyor | Pil optimizasyonunu **Kısıtlanmamış** yapın (Bölüm 1). |
| Android Auto güncellemesinden sonra kayboldu | Geliştirici ayarlarındaki **Bilinmeyen kaynaklar** bazen kapanır. Yeniden açın. |
| Orijinal Fermata da kurulu | Sorun değil. Bu sürümün paket adı farklıdır, ikisi ayrı uygulama olarak görünür. Karışıklığı önlemek için birini kaldırabilirsiniz. |

## 5. İleri düzey ve isteğe bağlı özellikler

Bunların hiçbiri normal kullanım için gerekli değildir. Hepsi varsayılan olarak kapalıdır.

- **Fermata Mirror (ekran yansıtma):** Telefon ekranını araca yansıtır. Gerekenler: Android **Erişilebilirlik** ayarlarından Fermata hizmetini açmak (dokunmaları telefona iletmek için) ve ilk açılışta ekran kaydı iznini onaylamak.
- **Root / LSPosed:** Root izni verilirse yansıtma izni otomatik ayarlanır. LSPosed modülü etkinleştirilirse Android Auto'nun "Play Store'dan mı kuruldu" denetimi atlanır. Bu modül etkinken Android Auto içinde **tüm** uygulamalar Play'den kurulmuş gibi görünür. Bu kılavuzdaki "Bilinmeyen kaynaklar" yöntemi zaten yeterlidir, LSPosed önerilmez.

## 6. Güvenlik incelemesi: ne bulundu, ne değişti

Kaynak kodun tamamı (Java, manifest dosyaları, build betikleri, CI tanımları ve ikili dosyalar) incelendi. **Zararlı yazılım bulunmadı:** reklam SDK'sı, analitik/izleme, kripto madencisi, gizli sunucu, şifrelenmiş yük veya internetten kod indirme yok. Cihaz kimliği, rehber, SMS ya da arama kaydı okunmuyor. Paketteki ikili dosyalar da kontrol edildi. `aauto.aar` yalnızca Google Android Auto SDK sınıflarını içeriyor. Xposed jar'ı yalnızca derleme zamanı stub'larından oluşuyor ve APK'ya girmiyor. `gradle-wrapper.jar` resmi Gradle sağlama toplamıyla birebir eşleşiyor.

Bununla birlikte **kötü niyetli olmayan ama ciddi güvenlik açıkları** ve gereksiz bazı özellikler vardı. Bu sürümde bunlar düzeltildi ya da kaldırıldı:

| # | Bulgu | Bu sürümde |
|---|---|---|
| 1 | Uygulamanın kendi HTTPS istemcisi **hiçbir sertifikayı doğrulamıyordu** (trust-all). Aynı Wi-Fi'daki biri bağlantıları dinleyip değiştirebilirdi. | **Düzeltildi:** sistem sertifika deposu ve alan adı (hostname) doğrulaması zorunlu. |
| 2 | Dışa açık `ContentProvider`, telefondaki **herhangi bir uygulamanın** Fermata'nın özel dosyalarını okumasına izin veriyordu. SMB/SFTP şifreleri de bu dosyalarda tutuluyor. | **Düzeltildi:** yalnızca resim dosyaları ve kapak görseli önbelleği okunabilir. Uygulama verileri erişime kapalı. |
| 3 | Otomatik güncelleme, orijinal geliştiricinin GitHub'ından APK indirip kendini **Play Store'dan kurulmuş gibi** gösteriyordu (doğrulamasız bağlantı üzerinden). | **Kaldırıldı**, uygulama kurma/silme izinleri de birlikte kaldırıldı. |
| 4 | Varsayılan imza anahtarı herkesin bildiği bir paroladan üretiliyordu. | Bu APK **size özel, rastgele üretilmiş** bir anahtarla imzalandı. |
| 5 | Tarayıcı eklentisi, DRM isteyen herhangi bir web sitesine **mikrofon ve kamera** veriyordu. Araç modunda ise sormadan veriyordu. | **Düzeltildi:** kamera web sayfalarına hiç verilmiyor. Mikrofon yalnızca siz telefonda izin verdiyseniz veriliyor. Kamera izni APK'dan kaldırıldı. |
| 6 | Root'lu cihazda dosya silme komutu, dosya adındaki `'` karakteriyle komut enjeksiyonuna açıktı. | **Düzeltildi:** dosya yolu kabuk için doğru biçimde tırnaklanıyor. |
| 7 | Ayarlar, kayıtlı şifrelerle birlikte Google bulut yedeğine gidiyordu. | **Bulut yedeği kapatıldı.** Ayarları başka bir telefona taşımak için Ayarlar → **Tercihleri dışa dosyaya aktar** / **Tercihleri dosyadan içe aktar** kullanılabilir. |
| 8 | Kullanılmayan `READ_PHONE_STATE` izni. | **Kaldırıldı.** |
| 9 | Araçta gereksiz ve veri gönderen ya da Google Play Services'e bağımlı modüller: ChatGPT sohbeti (OpenAI'ye metin gönderir), POI/radar uyarısı (konumu üçüncü tarafa gönderir), ML Kit çeviri, Chromecast, Whisper altyazı üretimi, OpusMT çeviri. | **Derlemeden çıkarıldı.** APK 158 MB'tan 61 MB'a düştü. |

Bilinen, bilerek dokunulmayan noktalar:
- Uygulama çöktüğünde, **siz onaylarsanız**, çökme kaydını orijinal geliştiriciye e-postayla gönderme seçeneği sunar. Otomatik bir şey gönderilmez.
- Tarayıcı ve YouTube eklentileri doğal olarak açtığınız sitelere bağlanır. Bağış bağlantıları yalnızca dokunursanız açılır.

## 7. Güncelleme

Otomatik güncelleme kaldırıldı. Yeni sürüm için kaynak kod aynı şekilde incelenip yeniden derlenmelidir. Yeni APK **aynı anahtarla** imzalanırsa mevcut kurulumun üzerine, ayarlar korunarak kurulur. Anahtar dosyası (`keys/fermata-car.jks`) ve parolası (`local.properties`) derleme makinesinde saklanır. Kaybolursa güncelleme yapılamaz, uygulamayı kaldırıp yeniden kurmanız gerekir.

## 8. Kaynaktan derleme (geliştirici için)

```bash
git clone https://github.com/AndreyPavlenko/Fermata.git && cd Fermata
# bu değişiklikleri uygula (car-build dalı / yama dosyası)
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"   # JDK 21 (JDK 17.0.7 javac hatası veriyor)
export ANDROID_SDK_ROOT=~/Library/Android/sdk NO_GS=true
# local.properties: sdk.dir, storeFile, keyAlias, keyPassword, storePassword ve
# excludeModules=whisper,mlkit,chat,cast,poi,opusmt
./gradlew fermata:packageAutoReleaseUniversalApk -PABI=arm64-v8a -PAPP_ID_SFX=.car
# çıktı: fermata/build/outputs/apk_from_bundle/autoRelease/fermata-2.0.2-auto-release-universal.apk
```

Yeni `EXCLUDE_MODULES` / `excludeModules` seçeneği, istenmeyen eklenti modüllerini derlemeden çıkarmak için eklendi.
