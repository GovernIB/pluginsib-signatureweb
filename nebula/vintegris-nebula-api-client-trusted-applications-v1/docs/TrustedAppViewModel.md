

# TrustedAppViewModel


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**appId** | **String** | UUID of the trusted app |  |
|**tenantId** | **String** | Tenant ID |  |
|**name** | **String** | Name defined in the trusted app |  |
|**status** | [**StatusEnum**](#StatusEnum) | Current status of the trusted app |  |
|**permissions** | **List&lt;String&gt;** | List of permissions assigned to the trusted app |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| ENABLED | &quot;ENABLED&quot; |
| DISABLED | &quot;DISABLED&quot; |



