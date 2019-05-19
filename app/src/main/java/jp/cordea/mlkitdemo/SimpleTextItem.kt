package jp.cordea.mlkitdemo

import com.xwray.groupie.databinding.BindableItem
import jp.cordea.mlkitdemo.databinding.ItemSimpleTextBinding

class SimpleTextItemModel(val title: String)

class SimpleTextItem(
    private val model: SimpleTextItemModel
) : BindableItem<ItemSimpleTextBinding>() {
    override fun getLayout(): Int = R.layout.item_simple_text

    override fun bind(viewBinding: ItemSimpleTextBinding, position: Int) {
        viewBinding.model = model
    }
}
