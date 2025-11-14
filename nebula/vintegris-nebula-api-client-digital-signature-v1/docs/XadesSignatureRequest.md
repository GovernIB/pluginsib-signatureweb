

# XadesSignatureRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**certId** | **String** | This parameter is linked with the signingId response parameter of the get user certificates operation |  [optional] |
|**certPin** | **String** | PIN of the selected certificate |  [optional] |
|**certTicket** | **String** |  |  [optional] |
|**commitmentTypes** | **List&lt;String&gt;** |  |  [optional] |
|**company** | **String** |  |  [optional] |
|**digestAlgorithm** | [**DigestAlgorithmEnum**](#DigestAlgorithmEnum) | Algorithm used to obtain the digest of the document to sign |  [optional] |
|**fileHash** | **String** |  |  [optional] |
|**fileHashDigestAlgorithm** | **String** |  |  [optional] |
|**fileHashName** | **String** |  |  [optional] |
|**policyDigestAlgorithm** | **String** |  |  [optional] |
|**policyHash** | **String** |  |  [optional] |
|**policyId** | **String** |  |  [optional] |
|**policyQualifier** | **String** |  |  [optional] |
|**signContainer** | **String** |  |  [optional] |
|**signLevel** | [**SignLevelEnum**](#SignLevelEnum) | Signature level |  [optional] |
|**signLocation** | [**XadesSignLocation**](XadesSignLocation.md) |  |  [optional] |
|**signPackage** | [**SignPackageEnum**](#SignPackageEnum) | Signature Mode |  [optional] |
|**signerRoles** | **List&lt;String&gt;** |  |  [optional] |
|**tsaURL** | **String** | URL of a Timestamping authority to be used in T, LT or LTA signature levels |  [optional] |
|**tsaUSR** | **String** | User of the credentials of the Timestamping authority, if authentication is required |  [optional] |
|**tsaPWD** | **String** | Password of the credentials of the Timestamping authority, if authentication is required |  [optional] |
|**userId** | **String** |  |  [optional] |
|**userUuid** | **String** |  |  [optional] |



## Enum: DigestAlgorithmEnum

| Name | Value |
|---- | -----|
| SHA1 | &quot;SHA1&quot; |
| SHA256 | &quot;SHA256&quot; |
| SHA384 | &quot;SHA384&quot; |
| SHA512 | &quot;SHA512&quot; |



## Enum: SignLevelEnum

| Name | Value |
|---- | -----|
| B | &quot;B&quot; |
| T | &quot;T&quot; |
| LT | &quot;LT&quot; |
| LTA | &quot;LTA&quot; |



## Enum: SignPackageEnum

| Name | Value |
|---- | -----|
| DETACHED | &quot;DETACHED&quot; |
| ENVELOPED | &quot;ENVELOPED&quot; |
| ENVELOPING | &quot;ENVELOPING&quot; |



