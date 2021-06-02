package com.example.navigasiapp.adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.zaich.login_marketplace.Model.CategoryModel
import com.zaich.login_marketplace.ProductActivity
import com.zaich.login_marketplace.R
import kotlinx.android.synthetic.main.item_layout.view.*
import java.net.URL


class categoryAdapter (val context: Context) :
    RecyclerView.Adapter<categoryAdapter.ViewHolder>(), Filterable {
    var arrayList = ArrayList<CategoryModel>()
    var CategoryListFilter = ArrayList<CategoryModel>()

    fun setData(arrayList: ArrayList<CategoryModel>) {
        this.arrayList = arrayList
        this.CategoryListFilter = arrayList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(model: CategoryModel) {
            itemView.categoryName.text   = "${model.name}"
            //Menampilkan gambar yang didapatkan dari url
            val url: URL = URL("http://rezaich.teknisitik.com/" + model.image_link)
            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            itemView.imgCategory.setImageBitmap(bmp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return categoryAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(arrayList[position])
        holder.itemView.setOnClickListener() {
            val model = arrayList.get(position)

            var categoryId: Int = model.id


            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("categoryId", categoryId)
            context.startActivity(intent)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?):
                    FilterResults {
                val filterResults = FilterResults()
                if (constraint == null || constraint.length < 0) {
                    filterResults.count = CategoryListFilter.size
                    filterResults.values = CategoryListFilter
                } else {
                    var searchChr = constraint.toString()
                    val categorySearch = ArrayList<CategoryModel>()
                    for (item in CategoryListFilter) {
                        if (item.name.toLowerCase()
                                .contains(searchChr)) {
                            categorySearch.add(item)
                        }
                    }
                    filterResults.count = categorySearch.size
                    filterResults.values = categorySearch
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?,
                                        filterResults: FilterResults?) {
                arrayList = filterResults!!.values as ArrayList<CategoryModel>
                notifyDataSetChanged()
            }
        }
    }
}