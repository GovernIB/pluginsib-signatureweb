

# DetailedTrustedAppViewModel


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**appId** | **String** | UUID of the trusted app |  |
|**tenantId** | **String** | Tenant ID |  |
|**name** | **String** | Name defined in the trusted app |  |
|**status** | [**StatusEnum**](#StatusEnum) | Current status of the trusted app |  |
|**permissions** | **List&lt;String&gt;** | List of permissions assigned to the trusted app |  |
|**description** | **String** | Description defined in the trusted app |  |
|**groups** | **List&lt;String&gt;** | List of groups defined in the trusted app |  |
|**jwtSignatureAlgorithm** | [**JwtSignatureAlgorithmEnum**](#JwtSignatureAlgorithmEnum) | Algorithm defined to generate the token to perform the authorisation |  |
|**expiration** | **Integer** | Expiration time (in seconds) defined for the token generated when the trusted app is authorised |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| ENABLED | &quot;ENABLED&quot; |
| DISABLED | &quot;DISABLED&quot; |



## Enum: JwtSignatureAlgorithmEnum

| Name | Value |
|---- | -----|
| SHA256 | &quot;HMAC_SHA256&quot; |
| SHA384 | &quot;HMAC_SHA384&quot; |
| SHA512 | &quot;HMAC_SHA512&quot; |



