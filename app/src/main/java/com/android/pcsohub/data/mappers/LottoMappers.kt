package com.android.pcsohub.data.mappers

import com.android.pcsohub.common.LottoAssetIcons
import com.android.pcsohub.common.LottoTitles
import com.android.pcsohub.common.LottoTitles.titles_arrangement
import com.android.pcsohub.data.local.LottoEntity
import com.android.pcsohub.data.remote.dto.LottoDtoItem
import com.android.pcsohub.domain.model.GroupedLotto
import com.android.pcsohub.domain.model.Lotto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

suspend fun List<LottoDtoItem>.toLottoEntityList(defaultDispatcher: CoroutineDispatcher): List<LottoEntity> {
    return withContext(defaultDispatcher) {
        map { dto ->
            val lottoEntity = LottoEntity(
                id = dto.id,
                contentBadge = dto.contentBadge,
                contentBody = dto.contentBody,
                contentData1 = dto.contentData1,
                contentData2 = dto.contentData2,
                contentId = dto.contentId,
                contentSubtitle1 = dto.contentSubtitle1,
                contentSubtitle2 = dto.contentSubtitle2,
                contentTitle = dto.contentTitle,
                contentUrl = dto.contentUrl
            )

            val lottoIconMap = mapOf(
                LottoTitles.ultralotto to LottoAssetIcons.ultralotto,
                LottoTitles.grandlotto to LottoAssetIcons.grandlotto,
                LottoTitles.superlotto to LottoAssetIcons.superlotto,
                LottoTitles.megalotto to LottoAssetIcons.megalotto,
                LottoTitles.lotto to LottoAssetIcons.lotto,
                LottoTitles.sixd to LottoAssetIcons.sixd,
                LottoTitles.fourd to LottoAssetIcons.fourd,
                LottoTitles.threed_9pm to LottoAssetIcons.threed,
                LottoTitles.threed_2pm to LottoAssetIcons.threed,
                LottoTitles.threed_5pm to LottoAssetIcons.threed,
                LottoTitles.twod_9pm to LottoAssetIcons.twod,
                LottoTitles.twod_2pm to LottoAssetIcons.twod,
                LottoTitles.twod_5pm to LottoAssetIcons.twod
            )

            lottoEntity.copy(contentPhoto = lottoIconMap[lottoEntity.contentTitle])
        }
    }
}

suspend fun List<LottoEntity>.toGroupedLottoList(defaultDispatcher: CoroutineDispatcher): List<GroupedLotto> {
    val sortDateByAsc = Comparator<GroupedLotto> { o1, o2 ->
        val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy (EEEE)")

        val o1Date = LocalDate.parse(o1.date, formatter)
        val o2Date = LocalDate.parse(o2.date, formatter)

        o2Date?.compareTo(o1Date) ?: -1
    }

    val lotto = withContext(defaultDispatcher) {
        map { entity ->
            Lotto(
                contentBadge = entity.contentBadge,
                contentBody = entity.contentBody,
                contentPhoto = entity.contentPhoto,
                contentSubtitle1 = entity.contentSubtitle1,
                contentSubtitle2 = entity.contentSubtitle2,
                contentTitle = entity.contentTitle
            )
        }.sortedWith(compareBy { titles_arrangement.indexOf(it.contentTitle) })
    }

    return withContext(defaultDispatcher) {
        lotto.groupBy { it.contentSubtitle1 }.map {
            GroupedLotto(date = it.key, lotto = it.value)
        }.sortedWith(sortDateByAsc)
    }
}