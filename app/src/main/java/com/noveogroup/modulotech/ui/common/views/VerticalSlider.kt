package com.noveogroup.modulotech.ui.common.views

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.noveogroup.modulotech.ui.theme.regularPadding
import java.lang.Float.max
import kotlin.math.min

private val verticalSliderWidth = 4.dp
private val thumbSize = 20.dp
private val ThumbDefaultElevation = 1.dp
private val ThumbDisabledElevation = 0.dp
private val ThumbPressedElevation = 6.dp
private val ThumbRippleRadius = 24.dp

@Composable
fun VerticalSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SliderColors = SliderDefaults.colors(),
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .requiredSizeIn(minWidth = thumbSize, minHeight = thumbSize * 2)
            .focusable(enabled, interactionSource),
    ) {
        val maxHeightPx: Float = max(constraints.maxHeight.toFloat(), 0f)
        val minHeightPx: Float = min(0f, maxHeightPx)

        var thumbOffset by remember {
            mutableStateOf(
                scaleValueToRange(
                    value = value,
                    fromRange = valueRange,
                    toRange = minHeightPx..maxHeightPx,
                ),
            )
        }

        val draggableState = remember(minHeightPx, maxHeightPx, valueRange) {
            DraggableState { delta ->
                val newOffset = thumbOffset - delta
                thumbOffset = when {
                    newOffset >= maxHeightPx -> maxHeightPx
                    newOffset <= minHeightPx -> minHeightPx
                    else -> newOffset
                }
                onValueChange(
                    scaleValueToRange(
                        value = thumbOffset,
                        fromRange = minHeightPx..maxHeightPx,
                        toRange = valueRange,
                    ),
                )
            }
        }

        val thumbOffsetDp: Dp
        val heightDp: Dp
        with(LocalDensity.current) {
            thumbOffsetDp = thumbOffset.toDp()
            heightDp = (maxHeightPx - minHeightPx).toDp()
        }
        VerticalSliderView(
            height = heightDp,
            thumbOffset = thumbOffsetDp,
            enabled = enabled,
            modifier = Modifier
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Vertical,
                    enabled = enabled,
                    interactionSource = interactionSource,
                ),
            colors = colors,
            interactionSource = interactionSource,
        )
    }
}

@Composable
private fun BoxScope.VerticalSliderView(
    height: Dp,
    thumbOffset: Dp,
    enabled: Boolean,
    modifier: Modifier,
    colors: SliderColors,
    interactionSource: MutableInteractionSource,
) {
    val thumbRadius = thumbSize / 2
    Spacer(
        Modifier
            .align(Alignment.Center)
            .width(verticalSliderWidth)
            .height(height)
            .background(colors.trackColor(enabled = enabled, active = false).value),
    )
    Spacer(
        Modifier
            .align(Alignment.BottomCenter)
            .width(verticalSliderWidth)
            .height(thumbOffset - thumbRadius)
            .background(colors.trackColor(enabled = enabled, active = true).value),
    )
    SliderThumb(
        thumbSize = thumbSize,
        offset = height - thumbOffset - thumbRadius,
        enabled = enabled,
        modifier = modifier,
        colors = colors,
        interactionSource = interactionSource,
    )
}

@Composable
private fun BoxScope.SliderThumb(
    thumbSize: Dp,
    offset: Dp,
    enabled: Boolean,
    modifier: Modifier,
    colors: SliderColors,
    interactionSource: MutableInteractionSource,
) {
    Box(
        Modifier
            .offset(y = offset)
            .align(Alignment.TopCenter)
            .then(modifier),
    ) {
        val interactions = remember { mutableStateListOf<Interaction>() }
        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> interactions.add(interaction)
                    is PressInteraction.Release -> interactions.remove(interaction.press)
                    is PressInteraction.Cancel -> interactions.remove(interaction.press)
                    is DragInteraction.Start -> interactions.add(interaction)
                    is DragInteraction.Stop -> interactions.remove(interaction.start)
                    is DragInteraction.Cancel -> interactions.remove(interaction.start)
                }
            }
        }

        val elevation = when {
            !enabled -> ThumbDisabledElevation
            interactions.isNotEmpty() -> ThumbPressedElevation
            else -> ThumbDefaultElevation
        }
        Spacer(
            modifier
                .size(thumbSize)
                .indication(
                    interactionSource = interactionSource,
                    indication = rememberRipple(bounded = false, radius = ThumbRippleRadius),
                )
                .hoverable(interactionSource = interactionSource)
                .shadow(elevation, CircleShape, clip = false)
                .background(colors.thumbColor(enabled = enabled).value, CircleShape),
        )
    }
}

/**
 * Scale [value] from [fromRange] range to [toRange] range
 */
private fun scaleValueToRange(
    value: Float,
    fromRange: ClosedRange<Float>,
    toRange: ClosedRange<Float>,
): Float {
    val coercedValue = value.coerceIn(fromRange.start, fromRange.endInclusive)
    return (coercedValue / fromRange.endInclusive) * (toRange.endInclusive - toRange.start) + toRange.start
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewVerticalSlider() {
    var value by remember { mutableStateOf(0.5f) }
    VerticalSlider(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier
            .padding(regularPadding)
            .fillMaxWidth()
            .height(200.dp),
    )
}
