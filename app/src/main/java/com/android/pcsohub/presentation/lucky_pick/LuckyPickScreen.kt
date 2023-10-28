package com.android.pcsohub.presentation.lucky_pick

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.pcsohub.R
import com.android.pcsohub.common.LottoAssetIcons
import com.android.pcsohub.common.LottoTitles
import com.android.pcsohub.presentation.components.FloatingActionButtonHideOnScroll
import com.android.pcsohub.presentation.lucky_pick.components.LuckyPickListItem
import com.android.pcsohub.ui.theme.PCSOBallAPITheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LuckyPickScreen(
    modifier: Modifier = Modifier, viewModel: LuckyPickViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val listState = rememberLazyListState()

    val fabVisibility by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is LuckyPickViewModel.UIEvent.ShowSnackbar -> {
                    event.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    StatelessScreen(modifier = modifier,
                    visibilityState = fabVisibility,
                    listState = listState,
                    state = state,
                    onUltraLottoGenerate = {
                        viewModel.onEvent(LuckyPickEvent.SelectUltraLotto)
                    },
                    onGrandLottoGenerate = {
                        viewModel.onEvent(LuckyPickEvent.SelectGrandLotto)
                    },
                    onSuperLottoGenerate = {
                        viewModel.onEvent(LuckyPickEvent.SelectSuperLotto)
                    },
                    onMegaLottoGenerate = {
                        viewModel.onEvent(LuckyPickEvent.SelectMegaLotto)
                    },
                    onLottoGenerate = {
                        viewModel.onEvent(LuckyPickEvent.SelectLotto)
                    },
                    onSixDGenerate = {
                        viewModel.onEvent(LuckyPickEvent.SelectSixD)
                    },
                    onFourDGenerate = {
                        viewModel.onEvent(LuckyPickEvent.SelectFourD)
                    },
                    onThreeDGenerate = {
                        viewModel.onEvent(LuckyPickEvent.SelectThreeD)
                    },
                    onTwoDGenerate = {
                        viewModel.onEvent(LuckyPickEvent.SelectTwoD)
                    },
                    onFabClick = {
                        viewModel.onEvent(LuckyPickEvent.Save)
                    })
}

@Composable
private fun StatelessScreen(
    modifier: Modifier = Modifier,
    visibilityState: Boolean,
    listState: LazyListState,
    state: LuckPickState,
    onUltraLottoGenerate: () -> Unit,
    onGrandLottoGenerate: () -> Unit,
    onSuperLottoGenerate: () -> Unit,
    onMegaLottoGenerate: () -> Unit,
    onLottoGenerate: () -> Unit,
    onSixDGenerate: () -> Unit,
    onFourDGenerate: () -> Unit,
    onThreeDGenerate: () -> Unit,
    onTwoDGenerate: () -> Unit,
    onFabClick: () -> Unit
) {
    Box {
        LazyColumn(
            modifier = modifier, state = listState
        ) {
            item {
                LuckyPickListItem(
                    icon = LottoAssetIcons.ultralotto,
                    title = LottoTitles.ultralotto,
                    onGenerate = onUltraLottoGenerate,
                    lotto = state.ultraLotto
                )
            }

            item {
                LuckyPickListItem(
                    icon = LottoAssetIcons.grandlotto,
                    title = LottoTitles.grandlotto,
                    onGenerate = onGrandLottoGenerate,
                    lotto = state.grandLotto
                )
            }

            item {
                LuckyPickListItem(
                    icon = LottoAssetIcons.superlotto,
                    title = LottoTitles.superlotto,
                    onGenerate = onSuperLottoGenerate,
                    lotto = state.superLotto
                )
            }

            item {
                LuckyPickListItem(
                    icon = LottoAssetIcons.megalotto,
                    title = LottoTitles.megalotto,
                    onGenerate = onMegaLottoGenerate,
                    lotto = state.megaLotto
                )
            }

            item {
                LuckyPickListItem(
                    icon = LottoAssetIcons.lotto,
                    title = LottoTitles.lotto,
                    onGenerate = onLottoGenerate,
                    lotto = state.lotto
                )
            }


            item {
                LuckyPickListItem(
                    icon = LottoAssetIcons.sixd,
                    title = LottoTitles.sixd,
                    onGenerate = onSixDGenerate,
                    lotto = state.sixD
                )
            }

            item {
                LuckyPickListItem(
                    icon = LottoAssetIcons.fourd,
                    title = LottoTitles.fourd,
                    onGenerate = onFourDGenerate,
                    lotto = state.fourD
                )
            }

            item {
                LuckyPickListItem(
                    icon = LottoAssetIcons.threed,
                    title = LottoTitles.threed_no_time,
                    onGenerate = onThreeDGenerate,
                    lotto = state.threeD
                )
            }

            item {
                LuckyPickListItem(
                    icon = LottoAssetIcons.twod,
                    title = LottoTitles.twod_no_time,
                    onGenerate = onTwoDGenerate,
                    lotto = state.twoD
                )
            }
        }

        FloatingActionButtonHideOnScroll(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            visibilityState = visibilityState,
            onClick = onFabClick,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_save_24),
                contentDescription = "Save"
            )
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    PCSOBallAPITheme {
        Surface {
            StatelessScreen(visibilityState = false,
                            state = LuckPickState(),
                            listState = rememberLazyListState(),
                            onUltraLottoGenerate = {},
                            onGrandLottoGenerate = {},
                            onSuperLottoGenerate = {},
                            onMegaLottoGenerate = {},
                            onLottoGenerate = {},
                            onSixDGenerate = {},
                            onFourDGenerate = {},
                            onThreeDGenerate = {},
                            onTwoDGenerate = {},
                            onFabClick = {})
        }
    }
}