package com.example.domain.convector

import com.example.domain.entity.Episode
import com.example.domain.entity.EpisodeEntity

object EpisodeForEntity : BaseConvector<ArrayList<Episode>, ArrayList<EpisodeEntity>> {
    override fun conv(type: ArrayList<Episode>): ArrayList<EpisodeEntity> {
        var episodeEntity = ArrayList<EpisodeEntity>()
        for (i in type) {
            episodeEntity.add(
                EpisodeEntity(
                    episodeId = i.episodeId,
                    episodeName = i.episodeName,
                    episode = i.episode,
                    episodeAirFate = i.episodeAirFate
                )
            )
        }
        return episodeEntity
    }
}

object EntityForEpisode : BaseConvector<List<EpisodeEntity>, ArrayList<Episode>> {
    override fun conv(type: List<EpisodeEntity>): ArrayList<Episode> {
        var episode = ArrayList<Episode>()
        for (i in type) {
            episode.add(
                Episode(
                    episodeId = i.episodeId,
                    episodeName = i.episodeName,
                    episode = i.episode,
                    episodeAirFate = i.episodeAirFate,
                    episodeCharacters = null
                )
            )
        }
        return episode
    }
}

object EntityForSingleEpisode : BaseConvector<EpisodeEntity, Episode> {
    override fun conv(type: EpisodeEntity): Episode {
        val episode = Episode(
            episodeId = type.episodeId,
            episodeName = type.episodeName,
            episode = type.episode,
            episodeAirFate = type.episodeAirFate,
            episodeCharacters = null
        )
        return episode
    }
}
