package com.group8.mobilefinalproject.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group8.mobilefinalproject.R
import com.group8.mobilefinalproject.ui.theme.BudgetOver
import com.group8.mobilefinalproject.ui.theme.BudgetUnder
import com.group8.mobilefinalproject.ui.theme.BudgetWarning
import com.group8.mobilefinalproject.ui.theme.SunsetOrange
import com.group8.mobilefinalproject.ui.theme.VagabondBlue
import com.group8.mobilefinalproject.ui.theme.VagabondNavy

/**
 * Home screen (dashboard): greeting, city search, upcoming trip,
 * student deals, popular cities and suggested places.
 */
@Composable
fun HomeScreen(
    onTripClick: (Long) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { HomeHeader(greetingRes = uiState.greetingRes) }

        item {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                placeholder = { Text(stringResource(R.string.home_search_hint)) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            val trip = uiState.upcomingTrip
            if (trip != null) {
                UpcomingTripCard(trip = trip, onClick = { onTripClick(trip.id) })
            } else {
                Text(
                    text = stringResource(R.string.home_no_upcoming_trip),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item { OfferCard() }

        item { SectionHeader(titleRes = R.string.home_popular_cities) }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(uiState.popularCities) { city -> CityTile(city = city) }
            }
        }

        item { SectionHeader(titleRes = R.string.home_suggested_places) }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(uiState.suggestedPlaces) { place -> SuggestedPlaceCard(place = place) }
            }
        }

        // Extra space so the FAB does not cover the last item
        item { Spacer(modifier = Modifier.height(72.dp)) }
    }
}

@Composable
private fun HomeHeader(greetingRes: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(greetingRes),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(R.string.home_title),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        IconButton(onClick = { /* Notifications are not part of this project */ }) {
            Icon(
                Icons.Filled.Notifications,
                contentDescription = stringResource(R.string.home_notifications)
            )
        }
    }
}

@Composable
private fun UpcomingTripCard(trip: UpcomingTrip, onClick: () -> Unit) {
    val barColor = when (trip.budgetStatus) {
        BudgetStatus.UNDER -> BudgetUnder
        BudgetStatus.WARNING -> BudgetWarning
        BudgetStatus.OVER -> BudgetOver
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .background(Brush.linearGradient(listOf(VagabondBlue, VagabondNavy)))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.home_upcoming_trip),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (trip.daysUntil > 0) {
                        stringResource(R.string.home_days_until, trip.daysUntil)
                    } else {
                        stringResource(R.string.home_today)
                    },
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
            Text(
                text = trip.name,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.DateRange,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${trip.dateRange} · ${trip.location}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
            Row {
                Text(
                    text = stringResource(R.string.home_budget),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(R.string.home_budget_amount, trip.budgetSpent, trip.budgetTotal),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
            }
            LinearProgressIndicator(
                progress = { trip.budgetProgress.coerceAtMost(1f) },
                color = barColor,
                trackColor = Color.White.copy(alpha = 0.25f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            )
        }
    }
}

@Composable
private fun OfferCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SunsetOrange.copy(alpha = 0.12f))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(SunsetOrange),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Info, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = stringResource(R.string.home_offer_title), style = MaterialTheme.typography.labelLarge)
            Text(
                text = stringResource(R.string.home_offer_description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = stringResource(R.string.home_offer_action),
            style = MaterialTheme.typography.labelLarge,
            color = SunsetOrange
        )
    }
}

@Composable
private fun SectionHeader(titleRes: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(titleRes),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        TextButton(onClick = { /* TODO (M3): open the Explore tab */ }) {
            Text(stringResource(R.string.home_see_all))
        }
    }
}

// Background colors for the city tiles (repeats if there are more cities)
private val cityTileColors = listOf(
    listOf(Color(0xFF1E6FD9), Color(0xFF2EC4B6)),
    listOf(Color(0xFFFF8A3D), Color(0xFFE5484D)),
    listOf(Color(0xFF0B2545), Color(0xFF1E6FD9))
)

@Composable
private fun CityTile(city: PopularCity) {
    val colorIndex = city.name.length % cityTileColors.size
    Column(
        modifier = Modifier
            .size(width = 110.dp, height = 90.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Brush.linearGradient(cityTileColors[colorIndex]))
            .clickable { /* TODO (M3): open Explore for this city */ }
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = city.name,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = city.province,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.85f)
        )
    }
}

@Composable
private fun SuggestedPlaceCard(place: SuggestedPlace) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.width(160.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Brush.linearGradient(listOf(BudgetUnder, VagabondBlue))),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Place, contentDescription = null, tint = Color.White)
        }
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = place.name,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = place.city,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
                repeat(place.rating) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = SunsetOrange,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}
