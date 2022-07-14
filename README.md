# SAML 认证

- [identity-provider] 实现 saml idp 端

SAML2 SP ACS URL: http://localhost:8080/saml-sp/login/saml2/sso/test-sp

- [service-provider] 实现 saml sp 端

SAML2 SP Metadata：http://localhost:8080/saml-sp/saml2/service-provider-metadata/test-sp

# 构建证书
# openssl req -newkey rsa:2048 -new -nodes -x509 -days 36500 -keyout private.key -out public.cer # 这个命令构建证书没有读取证书颁发者不对
openssl req -newkey rsa:2048 -sha256 -nodes -x509 -days 36500 -keyout private.key -out public.crt 
