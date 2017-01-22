/**
 * Copyright (C) 2015. Keegan小钢（http://keeganlee.me）
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package yaohf.com.android.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.widget.TextView;

import java.util.List;

import adapter.VirtualAdapter;
import yaohf.com.android.R;
import yaohf.com.android.util.CouponPriceUtil;
import yaohf.com.model.CouponBO;
import yaohf.com.widget.recyclerview.ViewHolder;

/**
 * 券列表的Adapter
 *
 * @version 1.0 创建时间：15/6/28
 */
public class CouponListAdapter extends VirtualAdapter<CouponBO> {

    private Context mContext;
    private List<CouponBO> mList;
    public CouponListAdapter(Context context,List<CouponBO> l) {
        super(context,l, R.layout.item_list_coupon);
        mContext = context;
    }



    @Override
    public void convert(ViewHolder holder, CouponBO couponBO, int position) {
        TextView text_item_title = holder.getView(R.id.text_item_title);
        TextView  text_item_info = holder.getView(R.id.text_item_info);
        TextView  text_item_price = holder.getView(R.id.text_item_price);

        CouponBO coupon = mList.get(position);
        text_item_title.setText(coupon.getName());
        text_item_info.setText(coupon.getIntroduce());
        SpannableString priceString;
        // 根据不同的券类型展示不同的价格显示方式
        switch (coupon.getModelType()) {
            default:
            case CouponBO.TYPE_CASH:
                priceString = CouponPriceUtil.getCashPrice(mContext, coupon.getFaceValue(), coupon.getEstimateAmount());
                break;
            case CouponBO.TYPE_DEBIT:
                priceString = CouponPriceUtil.getVoucherPrice(mContext, coupon.getDebitAmount(), coupon.getMiniAmount());
                break;
            case CouponBO.TYPE_DISCOUNT:
                priceString = CouponPriceUtil.getDiscountPrice(mContext, coupon.getDiscount(), coupon.getMiniAmount());
                break;
        }
        text_item_price.setText(priceString);
    }



}
