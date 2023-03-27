package com.noveogroup.modulotech.ui.profile

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.ui.common.DrawableImage
import com.noveogroup.modulotech.ui.common.Snackbar
import com.noveogroup.modulotech.ui.common.formatters.DateTransformation
import com.noveogroup.modulotech.ui.profile.model.UserProfileField
import com.noveogroup.modulotech.ui.profile.model.UserProfileFields
import com.noveogroup.modulotech.ui.profile.model.UserProfileScreenState
import com.noveogroup.modulotech.ui.theme.darkThemeIconAnimationDuration
import com.noveogroup.modulotech.ui.theme.darkThemeIconAnimationEnd
import com.noveogroup.modulotech.ui.theme.darkThemeIconAnimationStart
import com.noveogroup.modulotech.ui.theme.halfPadding
import com.noveogroup.modulotech.ui.theme.profilePhotoSize
import com.noveogroup.modulotech.ui.theme.regularPadding
import com.noveogroup.modulotech.ui.theme.saveProfileButtonHeight
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = koinViewModel(),
) {
    val screenState by viewModel.screenState.collectAsState()
    val darkModeEnabled by viewModel.darkModeState.collectAsState()
    val message by viewModel.message.collectAsState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier,
        topBar = {
            ProfileTopAppBar(
                darkThemeEnabled = darkModeEnabled,
                toggleDarkTheme = viewModel::toggleDarkTheme
            )
        },
        content = { paddingValues ->
            ProfileScreenContent(
                modifier = Modifier.padding(paddingValues),
                screenState = screenState,
                fieldValueChanged = viewModel::fieldValueChanged,
                saveButtonClicked = viewModel::saveProfile
            )
            message?.let { Snackbar(it, scaffoldState.snackbarHostState) }
        }
    )
}

@Composable
private fun ProfileTopAppBar(
    darkThemeEnabled: Boolean,
    toggleDarkTheme: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.profile_screen_title)) },
        actions = {
            val iconRotation: Float by animateFloatAsState(
                targetValue = if (!darkThemeEnabled) darkThemeIconAnimationStart else darkThemeIconAnimationEnd,
                animationSpec = tween(durationMillis = darkThemeIconAnimationDuration)
            )
            IconButton(onClick = toggleDarkTheme) {
                Icon(
                    painter = painterResource(if (darkThemeEnabled) R.drawable.ic_sun else R.drawable.ic_moon),
                    contentDescription = stringResource(R.string.dark_theme_icon_description),
                    modifier = Modifier.rotate(iconRotation)
                )
            }
        }
    )
}

@Composable
private fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    screenState: UserProfileScreenState,
    fieldValueChanged: (UserProfileField, String) -> Unit,
    saveButtonClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(regularPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserPhoto(screenState.userPhoto)
        Spacer(modifier = Modifier.height(regularPadding))
        UserInformationSection(
            userSectionFields = screenState.userProfileFields.userSectionFields,
            fieldValueChanged = fieldValueChanged
        )
        Spacer(modifier = Modifier.height(regularPadding))
        UserAddressSection(
            userAddressSectionFields = screenState.userProfileFields.userAddressSectionFields,
            fieldValueChanged = fieldValueChanged
        )
        Spacer(modifier = Modifier.height(regularPadding))
        SaveButton(saveButtonClicked = saveButtonClicked)
    }
}

@Composable
private fun UserPhoto(
    @DrawableRes userPhoto: Int,
) {
    Image(
        painter = painterResource(userPhoto),
        contentDescription = stringResource(R.string.user_profile_photo_description),
        modifier = Modifier
            .size(profilePhotoSize)
            .clip(CircleShape)
    )
}

@Composable
private fun UserInformationSection(
    userSectionFields: List<UserProfileFields.Field>,
    fieldValueChanged: (UserProfileField, String) -> Unit,
) {
    Text(text = stringResource(R.string.Profile_user_section))
    Spacer(modifier = Modifier.height(regularPadding))
    userSectionFields.forEachIndexed { index, field ->
        UserProfileTextField(
            label = field.label(),
            fieldState = field,
            onValueChange = fieldValueChanged,
            hint = field.hint(),
            keyboardType = field.keyboardType(),
            visualTransformation = field.visualTransformation()
        )
        if (index != userSectionFields.lastIndex) Spacer(modifier = Modifier.height(halfPadding))
    }
}

@Composable
private fun UserAddressSection(
    userAddressSectionFields: List<UserProfileFields.Field>,
    fieldValueChanged: (UserProfileField, String) -> Unit,
) {
    Text(text = stringResource(R.string.Profile_user_address_section))
    Spacer(modifier = Modifier.height(regularPadding))
    userAddressSectionFields.forEachIndexed { index, field ->
        UserProfileTextField(
            label = field.label(),
            fieldState = field,
            onValueChange = fieldValueChanged,
            hint = field.hint(),
            keyboardType = field.keyboardType(),
            imeAction = if (index == userAddressSectionFields.lastIndex) ImeAction.Done else ImeAction.Next,
            visualTransformation = field.visualTransformation()
        )
        if (index != userAddressSectionFields.lastIndex) {
            Spacer(modifier = Modifier.height(halfPadding))
        }
    }
}

@Composable
private fun UserProfileTextField(
    label: String,
    fieldState: UserProfileFields.Field,
    onValueChange: (UserProfileField, String) -> Unit,
    hint: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    Column(verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = label) },
            placeholder = hint?.let { { Text(text = hint) } },
            value = fieldState.value,
            isError = fieldState.error != null,
            trailingIcon = fieldState.error?.let { { DrawableImage(image = R.drawable.ic_error) } },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            onValueChange = { onValueChange(fieldState.field, it) },
            visualTransformation = visualTransformation
        )
        if (fieldState.error != null) {
            Text(
                text = fieldState.error.errorText,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

@Composable
private fun SaveButton(saveButtonClicked: () -> Unit = {}) {
    Button(
        onClick = saveButtonClicked,
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(saveProfileButtonHeight)
    ) {
        Text(text = stringResource(R.string.Profile_save_button))
    }
}

@Composable
private fun UserProfileFields.Field.label(): String = stringResource(
    when (field) {
        UserProfileField.CITY -> R.string.Profile_city
        UserProfileField.STREET -> R.string.Profile_street
        UserProfileField.COUNTRY -> R.string.Profile_country
        UserProfileField.LAST_NAME -> R.string.Profile_last_name
        UserProfileField.BIRTHDATE -> R.string.Profile_birthdate
        UserProfileField.FIRST_NAME -> R.string.Profile_first_name
        UserProfileField.POSTAL_CODE -> R.string.Profile_postal_code
        UserProfileField.STREET_CODE -> R.string.Profile_street_code
    }
)

@Composable
private
fun UserProfileFields.Field.keyboardType(): KeyboardType = when (field) {
    UserProfileField.BIRTHDATE, UserProfileField.POSTAL_CODE -> KeyboardType.Number
    else -> KeyboardType.Text
}

@Composable
private fun UserProfileFields.Field.hint(): String? = when (field) {
    UserProfileField.BIRTHDATE -> stringResource(R.string.birthdate_format)
    else -> null
}

@Composable
private fun UserProfileFields.Field.visualTransformation(): VisualTransformation = when (field) {
    UserProfileField.BIRTHDATE -> DateTransformation(stringResource(R.string.birthdate_format))
    else -> VisualTransformation.None
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewProfileTopAppBar() {
    var darkThemeEnabled by remember { mutableStateOf(true) }
    ProfileTopAppBar(
        darkThemeEnabled = darkThemeEnabled,
        toggleDarkTheme = { darkThemeEnabled = !darkThemeEnabled }
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewUserPhoto() {
    UserPhoto(userPhoto = R.drawable.ic_user_photo)
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewUserProfileTextField() {
    var fieldState by remember {
        mutableStateOf(
            UserProfileFields.Field(
                field = UserProfileField.FIRST_NAME,
                value = "Name"
            )
        )
    }
    UserProfileTextField(
        label = "First name",
        fieldState = fieldState,
        onValueChange = { _, value ->
            fieldState = fieldState.copy(
                value = value,
                error = if (value == "Error") {
                    UserProfileFields.FieldError("Oops, it is an error")
                } else {
                    null
                }
            )
        }
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewProfileScreenContent() {
    var screenState by remember { mutableStateOf(UserProfileScreenState()) }
    ProfileScreenContent(
        screenState = screenState,
        fieldValueChanged = { field, value ->
            screenState = screenState.copy(
                userProfileFields = screenState.userProfileFields
                    .copyWithNewFieldValue(field, value)
            )
        },
        saveButtonClicked = {}
    )
}