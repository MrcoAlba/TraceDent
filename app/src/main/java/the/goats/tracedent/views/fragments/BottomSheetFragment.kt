package the.goats.tracedent.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import the.goats.tracedent.R
import the.goats.tracedent.views.activities.MainActivity

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mButMasInfo : Button
    private lateinit var mTviNombre : TextView
    private lateinit var mTviDireccion : TextView
    private lateinit var mTviRating : TextView

    private lateinit var activityParent : MainActivity


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityParent = requireActivity() as MainActivity
        mButMasInfo = activityParent.findViewById(R.id.butMasInfo)
        mTviNombre = activityParent.findViewById(R.id.txtNombre)
        mTviDireccion = activityParent.findViewById(R.id.txtDireccion)
        mTviRating = activityParent.findViewById(R.id.txtRating)


        fun getTxtNombre() : TextView {
            return mTviNombre
        }

    }
    fun getButton() : Button {
        return mButMasInfo
    }

}