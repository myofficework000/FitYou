package com.business.fityou.ui.composables.login

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.business.fityou.R


/*
* According to https://developers.google.com/identity/branding-guidelines,
*       the design must match what's shown in the guidelines for branding reason.
* Since the guideline requires the exact color, and nowhere else will use it,
*       it's probably not necessary to put those colors into Color.kt.
*/
@Composable
fun GoogleSignInButton(
    darkMode: Boolean = isSystemInDarkTheme(),
    onSignIn: () -> Unit
) {
    Button(
        onClick = onSignIn,
        modifier = Modifier
            .height(40.dp)
            .width(IntrinsicSize.Max),
        shape = RoundedCornerShape(1.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (darkMode) Color(0xFF4285F4) else Color.White
        ),
        contentPadding = PaddingValues(start = 1.dp, end = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(38.dp),
                shape = RoundedCornerShape(1.dp),
                backgroundColor = Color.White,
                elevation = 0.dp
            ) {
                Icon(
                    painterResource(R.drawable.google_sign_in_logo),
                    null,
                    Modifier.requiredSize(18.dp),
                    tint= Color.Unspecified
                )
            }

            Text(
                text = "Sign in with Google",
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                color = if (darkMode) Color.White else Color(0xFF554F4F),
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}



@Preview
@Composable
private fun GoogleSignInButtonLightPreview() {
    GoogleSignInButton(
        darkMode = false
    ) {}
}

@Preview
@Composable
private fun GoogleSignInButtonDarkPreview() {
    GoogleSignInButton(
        darkMode = true
    ) {}
}