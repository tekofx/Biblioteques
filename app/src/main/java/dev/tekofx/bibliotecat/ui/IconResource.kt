package dev.tekofx.bibliotecat.ui

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import dev.tekofx.bibliotecat.R

class IconResource private constructor(
    @DrawableRes private val resID: Int? = null,
    private val imageVector: ImageVector? = null,
    private val drawable: Drawable? = null
) {

    @Composable
    fun asPainterResource(): Painter {

        return when {
            resID != null -> painterResource(id = resID)
            imageVector != null -> rememberVectorPainter(image = imageVector)
            drawable != null -> rememberDrawablePainter(drawable)
            else -> {
                painterResource(R.drawable.sunny)
            }
        }

    }

    companion object {
        fun fromDrawableResource(@DrawableRes resID: Int): IconResource {
            return IconResource(resID = resID)
        }

        fun fromDrawableResource(drawable: Drawable): IconResource {
            return IconResource(drawable = drawable)
        }

        fun fromImageVector(imageVector: ImageVector?): IconResource {
            return IconResource(imageVector = imageVector)
        }
    }
}