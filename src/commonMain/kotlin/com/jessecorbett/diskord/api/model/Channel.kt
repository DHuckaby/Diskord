package com.jessecorbett.diskord.api.model

import kotlinx.serialization.*
import kotlinx.serialization.internal.IntDescriptor
import kotlinx.serialization.internal.StringDescriptor

@Serializable
data class Channel(
    @SerialName("id") val id: String,
    @SerialName("type") val type: ChannelType,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("position") var position: Int? = null,
    @SerialName("permission_overwrites") var permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") var name: String? = null,
    @SerialName("topic") var topic: String? = null,
    @SerialName("nsfw") var nsfw: Boolean? = null,
    @SerialName("last_message_id") var lastMessageId: String? = null,
    @SerialName("bitrate") var voiceBitrate: Int? = null,
    @SerialName("user_limit") var voiceUserLimit: Int? = null,
    @SerialName("rate_limit_per_user") var rateLimitPerUser: Int? = null,
    @SerialName("recipients") var dmRecipients: List<User>? = null,
    @SerialName("icon") var iconHash: String? = null,
    @SerialName("owner_id") var dmOwnerId: String? = null,
    @SerialName("application_id") var applicationId: String? = null,
    @SerialName("parent_id") var parentId: String? = null,
    @SerialName("last_pin_timestamp") var lastPinTime: String? = null
)

@Serializable(with = ChannelTypeSerializer::class)
enum class ChannelType(val code: Int) {
    GUILD_TEXT(0),
    DM(1),
    GUILD_VOICE(2),
    GROUP_DM(3),
    GUILD_CATEGORY(4)
}

object ChannelTypeSerializer : KSerializer<ChannelType> {
    override val descriptor: SerialDescriptor = IntDescriptor.withName("ChannelTypeSerializer")

    override fun deserialize(decoder: Decoder): ChannelType {
        val target = decoder.decodeInt()
        return ChannelType.values().first {
            it.code == target
        }
    }

    override fun serialize(encoder: Encoder, obj: ChannelType) {
        encoder.encodeInt(obj.code)
    }
}

@Serializable
data class Overwrite(
    @SerialName("id") val id: String,
    @SerialName("type") val type: OverwriteType,
    @SerialName("allow") val allowed: Permissions,
    @SerialName("deny") val denied: Permissions
)

@Serializable(with = OverwriteTypeSerializer::class)
enum class OverwriteType(val value: String) {
    ROLE("role"),
    MEMBER("member")
}

object OverwriteTypeSerializer : KSerializer<OverwriteType> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("OverwriteTypeSerializer")

    override fun deserialize(decoder: Decoder): OverwriteType {
        val target = decoder.decodeString()
        return OverwriteType.values().first {
            it.value == target
        }
    }

    override fun serialize(encoder: Encoder, obj: OverwriteType) {
        encoder.encodeString(obj.value)
    }
}
