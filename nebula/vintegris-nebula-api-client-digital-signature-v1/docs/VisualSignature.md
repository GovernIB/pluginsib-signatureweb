

# VisualSignature

Required parameters in case that a visual signature is desired in the PDF file

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**xAxis** | **Float** | Horizontal position of the visible signature in pixels |  [optional] |
|**yAxis** | **Float** | Vertical position of the visible signature in pixels |  [optional] |
|**width** | **Integer** | Width of the visible signature in pixels. By default, the value will be the width of the image, images and/or the text defined |  [optional] |
|**height** | **Integer** | Height of the visible signature in pixels. By default, the value will be the height of the image, images and/or the text defined |  [optional] |
|**page** | **Integer** | Page where the visible signature will be inserted |  [optional] |
|**positionInPercentage** | **Boolean** | When true, xAxis and yAxis will be interpreted as percentages |  [optional] |
|**multiImageBuild** | [**MultiImageBuildEnum**](#MultiImageBuildEnum) | If there is more than one image for the visual signature, this parameter will define how this images will be merged into one image |  [optional] |
|**backgroundColor** | **List&lt;Integer&gt;** | The background color for the signature field in RGBA [0..255, 0..255, 0..255, 0..255]. By default, the color will be white with transparency |  [optional] |
|**imageVerticalAlignment** | [**ImageVerticalAlignmentEnum**](#ImageVerticalAlignmentEnum) | Vertical position of the visual signature in the page. In the case where the value is different than NONE and the parameter yAxis has a value different than the default one, the value defined by the parameter imageVerticalAlignment will prevail |  [optional] |
|**fontName** | [**FontNameEnum**](#FontNameEnum) | Name of the font that will be used for the text |  [optional] |
|**fontSize** | **Integer** | Letter size |  [optional] |
|**textColor** | **List&lt;Integer&gt;** | Text color in RGBA [0..255, 0..255, 0..255, 0..255]. The default text color is black without transparency |  [optional] |
|**text** | **String** | Text of the visual signature. It can contain the following macros for dynamic replacement: %CN% - Certificate Common Name, %DATE% - Signature date, %DATE_TIME% - Signature time |  [optional] |
|**textSignerPosition** | [**TextSignerPositionEnum**](#TextSignerPositionEnum) | In case there is an image and text defined for the visual signature, this parameter will define the position between these two elements in the visual signature. The default value implies that the image will be at the left part of the visible signature while the text will be at the right part |  [optional] |
|**textHorizontalAlignment** | [**TextHorizontalAlignmentEnum**](#TextHorizontalAlignmentEnum) | Text alignment |  [optional] |
|**textImageVerticalAlignment** | [**TextImageVerticalAlignmentEnum**](#TextImageVerticalAlignmentEnum) | In case there is more than one image with the parameter multiImageBuild with a value of HORIZONTAL and/or an image and text where the parameter textSignerPosition with values LEFT or RIGHT, this parameter defines the vertical alignment between those elements |  [optional] |
|**signLastPage** | **Boolean** | Flag to perform the signature on the last page, ignoring the value defined in the page attribute |  [optional] |
|**signatureField** | **String** | Indicates the name of the signature field in which the signature will be made. The field must previously exist in the PDF |  [optional] |



## Enum: MultiImageBuildEnum

| Name | Value |
|---- | -----|
| HORIZONTAL | &quot;HORIZONTAL&quot; |
| VERTICAL | &quot;VERTICAL&quot; |



## Enum: ImageVerticalAlignmentEnum

| Name | Value |
|---- | -----|
| NONE | &quot;NONE&quot; |
| TOP | &quot;TOP&quot; |
| MIDDLE | &quot;MIDDLE&quot; |
| BOTTOM | &quot;BOTTOM&quot; |



## Enum: FontNameEnum

| Name | Value |
|---- | -----|
| TIMES_ROMAN | &quot;TIMES_ROMAN&quot; |
| TIMES_BOLD | &quot;TIMES_BOLD&quot; |
| TIMES_ITALIC | &quot;TIMES_ITALIC&quot; |
| TIMES_BOLD_ITALIC | &quot;TIMES_BOLD_ITALIC&quot; |
| HELVETICA | &quot;HELVETICA&quot; |
| HELVETICA_BOLD | &quot;HELVETICA_BOLD&quot; |
| HELVETICA_OBLIQUE | &quot;HELVETICA_OBLIQUE&quot; |
| HELVETICA_BOLD_OBLIQUE | &quot;HELVETICA_BOLD_OBLIQUE&quot; |
| COURIER | &quot;COURIER&quot; |
| COURIER_BOLD | &quot;COURIER_BOLD&quot; |
| COURIER_OBLIQUE | &quot;COURIER_OBLIQUE&quot; |
| COURIER_BOLD_OBLIQUE | &quot;COURIER_BOLD_OBLIQUE&quot; |



## Enum: TextSignerPositionEnum

| Name | Value |
|---- | -----|
| TOP | &quot;TOP&quot; |
| BOTTOM | &quot;BOTTOM&quot; |
| RIGHT | &quot;RIGHT&quot; |
| LEFT | &quot;LEFT&quot; |



## Enum: TextHorizontalAlignmentEnum

| Name | Value |
|---- | -----|
| LEFT | &quot;LEFT&quot; |
| CENTER | &quot;CENTER&quot; |
| RIGHT | &quot;RIGHT&quot; |



## Enum: TextImageVerticalAlignmentEnum

| Name | Value |
|---- | -----|
| TOP | &quot;TOP&quot; |
| MIDDLE | &quot;MIDDLE&quot; |
| BOTTOM | &quot;BOTTOM&quot; |



