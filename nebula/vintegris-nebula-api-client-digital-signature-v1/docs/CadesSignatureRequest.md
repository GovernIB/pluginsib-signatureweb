

# CadesSignatureRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**signLevel** | [**SignLevelEnum**](#SignLevelEnum) | Signature level |  [optional] |
|**signPackage** | [**SignPackageEnum**](#SignPackageEnum) | Signature package |  [optional] |
|**digestAlgorithm** | [**DigestAlgorithmEnum**](#DigestAlgorithmEnum) | Algorithm used to obtain the digest of the document to sign |  [optional] |
|**certId** | **Integer** | This parameter is linked with the signingId response parameter of the get user certificates operation |  [optional] |
|**certPin** | **String** | PIN of the selected certificate, if needed |  [optional] |
|**policyId** | **String** | Identifier of the policy. The value is the OID. Can only be used if the signature level is B |  [optional] |
|**policyHash** | **String** | Hash of the policy to be used. Hexadecimal in Base64. Can only be used if the signature level is B |  [optional] |
|**policyDigestAlgorithm** | [**PolicyDigestAlgorithmEnum**](#PolicyDigestAlgorithmEnum) | Digest algorithm with which the policyHash was made. Can only be used if the signature level is B |  [optional] |
|**policyQualifier** | **String** | Defines a policy identifier qualifier. Can only be used if the signature level is B |  [optional] |
|**fileHash** | **String** | Defines a hash to be signed. Only used if Multipart File is not specified |  [optional] |
|**fileHashDigestAlgorithm** | [**FileHashDigestAlgorithmEnum**](#FileHashDigestAlgorithmEnum) | Algorithm used to generate fileHash. This parameter must have the same value as the &#39;digestAlgorithm&#39; parameter |  [optional] |
|**fileHashName** | **String** | Name of specified hash in data.fileHash |  [optional] |
|**tsaURL** | **String** | URL of a Timestamping authority to be used in T, LT or LTA signature levels |  [optional] |
|**tsaUSR** | **String** | User of the credentials of the Timestamping authority, if authentication is required |  [optional] |
|**tsaPWD** | **String** | Password of the credentials of the Timestamping authority, if authentication is required |  [optional] |
|**signLocation** | [**CadesSignLocation**](CadesSignLocation.md) |  |  [optional] |
|**signerRoles** | **List&lt;String&gt;** | List of roles of signer |  [optional] |
|**commitmentTypes** | [**List&lt;CommitmentTypesEnum&gt;**](#List&lt;CommitmentTypesEnum&gt;) | List of compromises of the signed document |  [optional] |
|**company** | **String** |  |  [optional] |
|**userUuid** | **String** |  |  [optional] |
|**userId** | **String** |  |  [optional] |
|**signContainer** | **String** |  |  [optional] |
|**certTicket** | **String** |  |  [optional] |



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
| ENVELOPING | &quot;ENVELOPING&quot; |



## Enum: DigestAlgorithmEnum

| Name | Value |
|---- | -----|
| SHA1 | &quot;SHA1&quot; |
| SHA256 | &quot;SHA256&quot; |
| SHA384 | &quot;SHA384&quot; |
| SHA512 | &quot;SHA512&quot; |



## Enum: PolicyDigestAlgorithmEnum

| Name | Value |
|---- | -----|
| SHA1 | &quot;SHA1&quot; |
| SHA256 | &quot;SHA256&quot; |
| SHA384 | &quot;SHA384&quot; |
| SHA512 | &quot;SHA512&quot; |



## Enum: FileHashDigestAlgorithmEnum

| Name | Value |
|---- | -----|
| SHA1 | &quot;SHA1&quot; |
| SHA256 | &quot;SHA256&quot; |
| SHA384 | &quot;SHA384&quot; |
| SHA512 | &quot;SHA512&quot; |



## Enum: List&lt;CommitmentTypesEnum&gt;

| Name | Value |
|---- | -----|
| PROOF_OF_ORIGIN | &quot;ProofOfOrigin&quot; |
| PROOF_OF_RECEIPT | &quot;ProofOfReceipt&quot; |
| PROOF_OF_DELIVERY | &quot;ProofOfDelivery&quot; |
| PROOF_OF_SENDER | &quot;ProofOfSender&quot; |
| PROOF_OF_APPROVAL | &quot;ProofOfApproval&quot; |
| PROOF_OF_CREATION | &quot;ProofOfCreation&quot; |



