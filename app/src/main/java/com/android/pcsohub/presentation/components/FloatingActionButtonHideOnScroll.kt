package com.android.pcsohub.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FloatingActionButtonHideOnScroll(
    modifier: Modifier = Modifier,
    visibilityState: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visibilityState,
        enter = fadeIn(),
        exit = fadeOut(animationSpec = keyframes {
            this.durationMillis = 120
        })
    ) {
        FloatingActionButton(
            onClick = onClick
        ) {
            content()
        }
    }
}
