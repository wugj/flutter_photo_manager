package top.kikt.imagescanner.thumb

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import io.flutter.plugin.common.MethodChannel
import top.kikt.imagescanner.Asset
import top.kikt.imagescanner.ResultHandler
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Created by debuggerx on 18-9-27 下午2:08
 */
object ThumbnailUtil {

    fun getThumbnailByGlide(ctx: Context, path: String, width: Int, height: Int, result: MethodChannel.Result) {
        val resultHandler = ResultHandler(result)
        Glide.with(ctx.applicationContext)
                .load(path)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(object : SimpleTarget<Bitmap>(width, height) {
                    override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>?) {
                        val bos = ByteArrayOutputStream()
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                        resultHandler.reply(bos.toByteArray())
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        resultHandler.reply(null)
                    }

                    override fun onLoadFailed(e: Exception, errorDrawable: Drawable?) {
                        resultHandler.reply(null)
                    }
                })

    }

    fun getThumbnailWithVideo(ctx: Context, asset: Asset, width: Int, height: Int, result: MethodChannel.Result) {
        val resultHandler = ResultHandler(result)

        Glide.with(ctx.applicationContext)
                .load(File(asset.path))
                .asBitmap()
                .into(object : SimpleTarget<Bitmap>(width, height) {
                    override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>?) {
                        val bos = ByteArrayOutputStream()
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                        resultHandler.reply(bos.toByteArray())
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        resultHandler.reply(null)
                    }
                })
    }

}
