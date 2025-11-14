

# PadesSignatureRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**signLevel** | [**SignLevelEnum**](#SignLevelEnum) | Signature level |  [optional] |
|**digestAlgorithm** | [**DigestAlgorithmEnum**](#DigestAlgorithmEnum) | Algorithm used to obtain the digest of the document to sign |  [optional] |
|**certId** | **String** | This parameter is linked with the signingId response parameter of the get user certificates operation |  |
|**certPin** | **String** | PIN of the selected certificate |  [optional] |
|**location** | **String** | The CPU host name or physical location of the signing |  [optional] |
|**signerRoles** | **List&lt;String&gt;** | List of roles of signer |  [optional] |
|**commitmentTypes** | [**List&lt;CommitmentTypesEnum&gt;**](#List&lt;CommitmentTypesEnum&gt;) | List of compromises of the signed document. This parameter shall be ignored if the parameter &#39;reason&#39; is defined |  [optional] |
|**tsaURL** | **String** | URL of a Timestamping authority to be used in T, LT or LTA signature levels |  [optional] |
|**tsaUSR** | **String** | User of the credentials of the Timestamping authority, if authentication is required |  [optional] |
|**tsaPWD** | **String** | Password of the credentials of the Timestamping authority, if authentication is required |  [optional] |
|**signatureSize** | **Integer** | Signature size |  [optional] |
|**visualSignature** | [**PadesVisualSignature**](PadesVisualSignature.md) |  |  [optional] |
|**dateFormat** | **String** | A date format pattern, based on Java&#39;s SimpleDateFormat specification, used for the %DATE% tag in the visual signature. |  [optional] |
|**timeZone** | **String** | The value must be a string corresponding to a valid IANA Time Zone Database name. |  [optional] |
|**certificationPermission** | [**CertificationPermissionEnum**](#CertificationPermissionEnum) | File permissions after signature |  [optional] |
|**application** | **String** | Application token that grants some permissions |  [optional] |
|**reason** | **String** | Reason for signature |  [optional] |
|**signerName** | **String** | Signer name |  [optional] |



## Enum: SignLevelEnum

| Name | Value |
|---- | -----|
| B | &quot;B&quot; |
| T | &quot;T&quot; |
| LT | &quot;LT&quot; |
| LTA | &quot;LTA&quot; |



## Enum: DigestAlgorithmEnum

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



## Enum: CertificationPermissionEnum

| Name | Value |
|---- | -----|
| NO_CHANGE_PERMITTED | &quot;NO_CHANGE_PERMITTED&quot; |
| MINIMAL_CHANGES_PERMITTED | &quot;MINIMAL_CHANGES_PERMITTED&quot; |
| CHANGES_PERMITTED | &quot;CHANGES_PERMITTED&quot; |



