

# GetMyCertificates200ResponseCertificatesListInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**certificateId** | **String** | Unique certificate identifier. |  |
|**signingId** | **String** | Signing identifier. |  [optional] |
|**alias** | **String** | Certificate alias or friendly name. |  [optional] |
|**subject** | **String** | Subject name (CN, OU, O...). |  |
|**issuer** | **String** | Issuer of the certificate. |  |
|**serialNumber** | **String** | Certificate serial number. |  |
|**dateValidStart** | **Long** | Start date of certificate validity. |  [optional] |
|**dateValidEnd** | **Long** | Expiration date of the certificate. |  [optional] |
|**status** | **String** | Current status (ISSUED, etc.). |  [optional] |
|**certificate** | **String** | Base64 encoded X.509 certificate. |  [optional] |
|**certType** | **String** | Type of certificate (IMPORTED, INTERMEDIATE, QUALIFIED). |  [optional] |
|**isUsable** | **String** | Indicates if the certificate is usable (1 for yes, 0 for no). |  [optional] |



