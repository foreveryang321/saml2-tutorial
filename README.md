# SAML 认证

- [identity-provider] 实现 saml idp 端
- [service-provider] 实现 saml sp 端

# 构建证书
openssl req -newkey rsa:2048 -new -nodes -x509 -days 36500 -keyout private.key -out public.cer
