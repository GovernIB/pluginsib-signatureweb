

# AuthorizeResponseViewModel


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**authorization** | **String** | Token generated for future operations in nebulaSUITE |  |
|**expiration** | **Long** | Token expiration time in minutes |  |
|**grants** | [**List&lt;GrantsEnum&gt;**](#List&lt;GrantsEnum&gt;) | Permissions held with the generated token |  |



## Enum: List&lt;GrantsEnum&gt;

| Name | Value |
|---- | -----|
| AUTH_FIRST | &quot;DISABLE_AUTH_FIRST&quot; |
| AUTH_SECOND | &quot;DISABLE_AUTH_SECOND&quot; |
| PIN_POLICY | &quot;DISABLE_PIN_POLICY&quot; |



