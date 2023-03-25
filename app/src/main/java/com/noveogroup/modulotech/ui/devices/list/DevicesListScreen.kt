package com.noveogroup.modulotech.ui.devices.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.devices.model.DeviceType
import com.noveogroup.modulotech.ui.common.DrawableImage
import com.noveogroup.modulotech.ui.common.Snackbar
import com.noveogroup.modulotech.ui.devices.list.model.DevicePreview
import com.noveogroup.modulotech.ui.devices.list.model.DevicesFilter
import com.noveogroup.modulotech.ui.theme.borderStroke
import com.noveogroup.modulotech.ui.theme.darkRed
import com.noveogroup.modulotech.ui.theme.dismissIconEndScale
import com.noveogroup.modulotech.ui.theme.dismissIconStartScale
import com.noveogroup.modulotech.ui.theme.dismissThresholdFraction
import com.noveogroup.modulotech.ui.theme.halfPadding
import com.noveogroup.modulotech.ui.theme.iconSize
import com.noveogroup.modulotech.ui.theme.lightGray
import com.noveogroup.modulotech.ui.theme.regularPadding
import com.noveogroup.modulotech.ui.theme.roundedCornerSize
import org.koin.androidx.compose.koinViewModel

@Composable
fun DevicesListScreen(
    modifier: Modifier = Modifier,
    viewModel: DevicesListViewModel = koinViewModel(),
    openDeviceDetails: (String) -> Unit,
) {
    val devices by viewModel.devices.collectAsState()
    val filters by viewModel.filters.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val message by viewModel.message.collectAsState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier,
        topBar = { DevicesListTopAppBar() },
        content = { paddingValues ->
            DevicesListContent(
                modifier = Modifier.padding(paddingValues),
                devices = devices,
                filters = filters,
                loading = loading,
                deviceClicked = { device -> openDeviceDetails(device.id) },
                deviceSwiped = viewModel::deleteDevice,
                filterClicked = viewModel::filterClicked,
                refresh = viewModel::refresh
            )
            message?.let { Snackbar(it, scaffoldState.snackbarHostState) }
        }
    )
}

@Composable
private fun DevicesListTopAppBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.devices_screen_title)) },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DevicesListContent(
    modifier: Modifier,
    devices: List<DevicePreview>,
    filters: List<DevicesFilter>,
    loading: Boolean,
    deviceClicked: (DevicePreview) -> Unit,
    deviceSwiped: (DevicePreview) -> Unit,
    filterClicked: (DevicesFilter) -> Unit,
    refresh: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(loading, refresh)
    Box(Modifier.pullRefresh(pullRefreshState)) {
        Column(modifier = modifier) {
            FilterChips(
                filters = filters,
                filterClicked = filterClicked
            )
            Spacer(modifier = Modifier.height(halfPadding))
            DevicesList(devices, deviceClicked, deviceSwiped)
        }
        PullRefreshIndicator(loading, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun FilterChips(filters: List<DevicesFilter>, filterClicked: (DevicesFilter) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(horizontal = halfPadding)
    ) {
        items(filters.size) { index ->
            FilterChip(
                filter = filters[index],
                filterClicked = filterClicked
            )
        }
    }
}

@Composable
private fun FilterChip(filter: DevicesFilter, filterClicked: (DevicesFilter) -> Unit) {
    Surface(
        modifier = Modifier.padding(halfPadding),
        shape = RoundedCornerShape(roundedCornerSize),
        color = if (filter.isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.surface,
        border = if (filter.isSelected) {
            null
        } else {
            BorderStroke(borderStroke, MaterialTheme.colors.secondary)
        },
    ) {
        Text(
            text = filter.title,
            style = MaterialTheme.typography.body1,
            color = if (filter.isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
            modifier = Modifier
                .clickable { filterClicked(filter) }
                .padding(halfPadding)
        )
    }
}

@Composable
private fun DevicesList(
    devices: List<DevicePreview>,
    deviceClicked: (DevicePreview) -> Unit,
    deviceSwiped: (DevicePreview) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            count = devices.size,
            key = { index -> devices[index].id }
        ) { index ->
            DismissedContent(
                contentDismissed = { deviceSwiped(devices[index]) }
            ) {
                DeviceCard(devices[index], deviceClicked)
            }
            Divider()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DismissedContent(
    contentDismissed: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val dismissState = rememberDismissState(
        confirmStateChange = { dismissValue ->
            if (dismissValue == DismissValue.DismissedToStart) {
                true.also { contentDismissed() }
            } else {
                false
            }
        }
    )
    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { FractionalThreshold(dismissThresholdFraction) },
        background = {
            if (dismissState.dismissDirection != DismissDirection.EndToStart) return@SwipeToDismiss
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    DismissValue.Default -> lightGray
                    else -> darkRed
                }
            )
            val scale by animateFloatAsState(
                targetValue = when (dismissState.targetValue) {
                    DismissValue.Default -> dismissIconStartScale
                    else -> dismissIconEndScale
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(regularPadding),
                contentAlignment = Alignment.CenterEnd
            ) {
                Image(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.delete_icon_description),
                    modifier = Modifier.scale(scale)
                )
            }
        },
        dismissContent = content
    )
}

@Composable
private fun DeviceCard(
    device: DevicePreview,
    deviceClicked: (DevicePreview) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { deviceClicked(device) }
            .background(MaterialTheme.colors.background)
            .padding(regularPadding),
    ) {
        DrawableImage(
            image = device.icon,
            contentDescription = R.string.device_icon_description,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.width(halfPadding))
        Column {
            Text(
                text = device.name,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = device.stateDescription,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewDevicesListTopAppBar() {
    DevicesListTopAppBar()
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewDeviceCard() {
    DeviceCard(
        device = DevicePreview(
            id = "0",
            name = "Light",
            icon = R.drawable.ic_light,
            stateDescription = "ON, 50"
        ),
        deviceClicked = {}
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewDevicesList() {
    var devices by remember {
        mutableStateOf(
            listOf(
                DevicePreview(
                    id = "0",
                    name = "Light",
                    icon = R.drawable.ic_light,
                    stateDescription = "14"
                ),
                DevicePreview(
                    id = "1",
                    name = "Roller shutter",
                    icon = R.drawable.ic_roller,
                    stateDescription = "50"
                ),
                DevicePreview(
                    id = "2",
                    name = "Heater",
                    icon = R.drawable.ic_heater,
                    stateDescription = "23 °C"
                ),
            )
        )
    }
    DevicesList(
        devices = devices,
        deviceSwiped = { device -> devices = devices.filter { it.id != device.id } },
        deviceClicked = {},
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewFilterChips() {
    var filters by remember {
        mutableStateOf(
            listOf(
                DevicesFilter(
                    title = "Light",
                    type = DeviceType.LIGHT,
                    isSelected = true
                ),
                DevicesFilter(
                    title = "Heater",
                    type = DeviceType.HEATER,
                    isSelected = false
                ),
                DevicesFilter(
                    title = "Roller shutter",
                    type = DeviceType.ROLLER_SHUTTER,
                    isSelected = true
                )
            )
        )
    }
    FilterChips(
        filters = filters,
        filterClicked = { clickedFilter ->
            filters = filters.map { filter ->
                if (filter.type == clickedFilter.type) {
                    filter.copy(isSelected = !filter.isSelected)
                } else {
                    filter
                }
            }
        }
    )
}