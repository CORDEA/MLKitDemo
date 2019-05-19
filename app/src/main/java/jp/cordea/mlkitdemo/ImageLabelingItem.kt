package jp.cordea.mlkitdemo

import com.xwray.groupie.databinding.BindableItem
import jp.cordea.mlkitdemo.databinding.ItemImageLabelingBinding

class ImageLabelingItemModel(val title: String)

class ImageLabelingItem(
    private val model: ImageLabelingItemModel
) : BindableItem<ItemImageLabelingBinding>() {
    override fun getLayout(): Int = R.layout.item_image_labeling

    override fun bind(viewBinding: ItemImageLabelingBinding, position: Int) {
        viewBinding.model = model
    }
}
