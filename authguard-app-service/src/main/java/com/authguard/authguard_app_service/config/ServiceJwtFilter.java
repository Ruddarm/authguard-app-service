// package com.authguard.authguard_app_service.config;

// import java.io.IOException;
// import java.util.UUID;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.authguard.authguard_app_service.services.JwtService;

// import io.jsonwebtoken.ExpiredJwtException;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor
// public class ServiceJwtFilter extends OncePerRequestFilter {

//     private final JwtService jwtService; 
//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {

//         final String tokenHeader = request.getHeader("Authorization");
//         try {
//             if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
//                 System.out.println("Header is null or something in UserJwt filter ");
//                 filterChain.doFilter(request, response);
//                 return;
//             }
//             String token = tokenHeader.split("Bearer ")[1];
//             UUID userId = jwtService.generateUserIdFromToken(token);
//             UserAuth userAuth = userService.loadUserById(userId)
//                     .orElseThrow(() -> new UsernameNotFoundException("User not Found"));
//             if (SecurityContextHolder.getContext().getAuthentication() == null) {

//                 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userAuth,
//                         null,
//                         null);
//                 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                 SecurityContextHolder.getContext().setAuthentication(authToken);
//             }
//             filterChain.doFilter(request, response);
//         } catch (ExpiredJwtException ex) {
//             // Directly write error to response

//             System.out.println(ex);
//             response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//             response.setContentType("application/json");
//             response.getWriter().write("{\"message\": \"Token is expired\"}");
//             response.getWriter().flush();
//         }

//     }

// }
