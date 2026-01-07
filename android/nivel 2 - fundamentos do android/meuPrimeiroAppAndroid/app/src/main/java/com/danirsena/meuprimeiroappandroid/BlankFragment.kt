package com.danirsena.meuprimeiroappandroid

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.danirsena.meuprimeiroappandroid.databinding.FragmentBlankBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "name"
private const val ARG_PARAM2 = "age"
private const val ARG_PARAM3 = "gender"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment() {
    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!

    private var param1: String? = null
    private var param2: Int? = null
    private var param3: Boolean? = null

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?): View? {
//        _binding = ResultProfileBindig.inflate(inflater, container, false)
//        return binding.root
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // se eu queor usar context em fragments, preciso do requireActivity()
        requireActivity().getString(R.string.name_age_gender)
        requireContext().getString(R.string.name_age_gender)
        context?.getString(R.string.name_age_gender) //pode ser nula
        getString(R.string.name_age_gender) // tem ligação direta com activity, então pode ser direto tambémF

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
            param3 = it.getBoolean(ARG_PARAM3)
        }
        Log.d("BlankFragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("BlankFragment", "onCreateView")
        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("BlankFragment", "onDestroyView")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("BlankFragment", "onViewCreated")
        binding.fragmentContent.text = getString(
            R.string.name_age_gender,
            param1,
            param2.toString(),
            if(param3 == true) "Masculino" else "Feminino"
        ).trimIndent()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("BlankFragment", "onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("BlankFragment", "onDetach")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(name: String, age: Int, gender: Boolean) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, name)
                    putInt(ARG_PARAM2, age)
                    putBoolean(ARG_PARAM3, gender)
                }
            }
    }
}