# spring-emlak-app
Spring Boot + Oracle DB ile geliştirilmiş emlak yönetim uygulaması. CRUD işlemleri, Bootstrap 5 arayüzü ve katmanlı mimari içerir.


Proje Kapsamı
Bu proje, bir emlak işletmesinin günlük operasyonlarını dijital ortamda takip edebilmesi için geliştirilmiş basit bir yönetim uygulamasıdır. Uygulama; işletme bilgileri, müşteri kayıtları, emlak tanımları ve kiralık/satılık emlak arama süreçlerini kapsayan temel modüllerden oluşur.

İşyeri Tanımı
Uygulamayı kullanacak olan emlak işletmesine ait bilgiler sisteme kaydedilir.
Bu bölümde işletmenin adı, yetkili kişi, adres, telefon ve fax gibi temel bilgiler tutulur.
Amaç, sistemde yapılan tüm işlemlerin hangi işletmeye ait olduğunun net şekilde belirlenmesidir.

Müşteri Tanımı
Emlak alan, satan, kiraya veren veya kiralamak isteyen tüm müşteriler bu bölümde kaydedilir.
Müşterinin adı, soyadı, iletişim bilgileri (ev telefonu, cep telefonu, e‑posta) gibi bilgiler saklanır.
Bu sayede her emlak kaydı veya arama işlemi doğru müşteriyle ilişkilendirilebilir.

Emlak Tanımı
Satıcı veya kiraya veren müşteriler tarafından emlakçıya teslim edilen tüm emlak kayıtları burada tutulur.
Emlak türü, metrekare, oda sayısı, kat bilgisi, bina yaşı, ısınma türü gibi özellikler kaydedilir.
Bu bölüm, emlak portföyünün düzenli şekilde yönetilmesini sağlar.

Emlak Arama
Kiralık veya satılık ev arayan müşteriler için bir arama ekranı bulunur.
Müşterinin talepleri (oda sayısı, metrekare, kat, ısınma türü vb.) girilir ve sisteme daha önce kaydedilmiş emlaklar arasından uygun olanlar listelenir.
Eğer kriterlere uygun bir emlak bulunursa, bu kaydın bilgileri yazdırılabilir
